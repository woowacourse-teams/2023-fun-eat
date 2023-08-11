package com.funeat.acceptance.member;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키를_얻는다;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.승인되지_않음;
import static com.funeat.acceptance.common.CommonSteps.잘못된_요청;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.member.MemberSteps.사용자_정보_수정_요청;
import static com.funeat.acceptance.member.MemberSteps.사용자_정보_조회_요청;
import static com.funeat.auth.exception.AuthErrorCode.LOGIN_MEMBER_NOT_FOUND;
import static com.funeat.exception.CommonErrorCode.REQUEST_VALID_ERROR_CODE;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.member.domain.Member;
import com.funeat.member.dto.MemberProfileResponse;
import com.funeat.member.dto.MemberRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends AcceptanceTest {

    @Nested
    class getMemberProfile_성공_테스트 {

        @Test
        void 사용자_정보를_확인하다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 사용자_정보_조회_요청(loginCookie);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            사용자_정보_조회를_검증하다(response, member);
        }
    }

    @Nested
    class getMemberProfile_실패_테스트 {

        @Test
        void 로그인_하지않은_사용자가_사용자_정보를_확인시_예외가_발생한다() {
            // given & when
            final var response = 사용자_정보_조회_요청(null);

            // then
            STATUS_CODE를_검증한다(response, 승인되지_않음);
            사용자_승인되지_않음을_검증하다(response);
        }
    }

    @Nested
    class putMemberProfile_성공_테스트 {

        @Test
        void 사용자_정보를_수정하다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var loginCookie = 로그인_쿠키를_얻는다();
            final var request = new MemberRequest("after", "http://www.after.com");

            // when
            final var response = 사용자_정보_수정_요청(loginCookie, request);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
        }
    }

    @Nested
    class putMemberProfile_실패_테스트 {

        @Test
        void 로그인_하지않은_사용자가_사용자_정보_수정시_예외가_발생한다() {
            // given
            final var request = new MemberRequest("after", "http://www.after.com");

            // when
            final var response = 사용자_정보_수정_요청(null, request);

            // then
            STATUS_CODE를_검증한다(response, 승인되지_않음);
            사용자_승인되지_않음을_검증하다(response);
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 사용자가_사용자_정보_수정할때_닉네임_미기입시_예외가_발생한다(final String nickname) {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var loginCookie = 로그인_쿠키를_얻는다();
            final var request = new MemberRequest(nickname, "http://www.after.com");

            // when
            final var response = 사용자_정보_수정_요청(loginCookie, request);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "닉네임을 확인해주세요. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 사용자가_사용자_정보_수정할때_이미지_미기입시_예외가_발생한다(final String image) {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var loginCookie = 로그인_쿠키를_얻는다();
            final var request = new MemberRequest("test", image);

            // when
            final var response = 사용자_정보_수정_요청(loginCookie, request);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "프로필 이미지를 확인해주세요. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }
    }

    private void RESPONSE_CODE와_MESSAGE를_검증한다(final ExtractableResponse<Response> response, final String expectedCode,
                                              final String expectedMessage) {
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.jsonPath().getString("code"))
                    .isEqualTo(expectedCode);
            softAssertions.assertThat(response.jsonPath().getString("message"))
                    .isEqualTo(expectedMessage);
        });
    }

    private void 사용자_정보_조회를_검증하다(final ExtractableResponse<Response> response, final Member member) {
        final var expected = MemberProfileResponse.toResponse(member);
        final var expectedNickname = expected.getNickname();
        final var expectedProfileImage = expected.getProfileImage();

        final var actualNickname = response.jsonPath().getString("nickname");
        final var actualProfileImage = response.jsonPath().getString("profileImage");

        assertSoftly(softAssertions -> {
            softAssertions.assertThat(actualNickname)
                    .isEqualTo(expectedNickname);
            softAssertions.assertThat(actualProfileImage)
                    .isEqualTo(expectedProfileImage);
        });
    }

    private void 사용자_승인되지_않음을_검증하다(final ExtractableResponse<Response> response) {
        final var expectedCode = LOGIN_MEMBER_NOT_FOUND.getCode();
        final var expectedMessage = LOGIN_MEMBER_NOT_FOUND.getMessage();

        final var actualCode = response.jsonPath().getString("code");
        final var actualMessage = response.jsonPath().getString("message");

        assertSoftly(softAssertions -> {
            softAssertions.assertThat(actualCode)
                    .isEqualTo(expectedCode);
            softAssertions.assertThat(actualMessage)
                    .isEqualTo(expectedMessage);
        });
    }
}
