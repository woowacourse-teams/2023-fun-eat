package com.funeat.acceptance.member;

import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.member.MemberSteps.사용자_정보_수정_요청;
import static com.funeat.acceptance.member.MemberSteps.사용자_정보_조회_요청;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.member.domain.Member;
import com.funeat.member.dto.MemberProfileResponse;
import com.funeat.member.dto.MemberRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    void 사용자_정보를_확인하다() {
        // given
        final var member = new Member("test", "http://www.test.com", "1");
        final var memberId = 멤버_추가_요청(member);

        // when
        final var response = 사용자_정보_조회_요청(memberId);

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
        사용자_정보_조회를_검증하다(response, member);
    }

    @Test
    void 사용자_정보를_수정하다() {
        // given
        final var member = new Member("before", "http://www.before.com", "1");
        final var memberId = 멤버_추가_요청(member);

        final var request = new MemberRequest("after", "http://www.after.com");

        // when
        final var response = 사용자_정보_수정_요청(memberId, request);

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
    }

    private Long 멤버_추가_요청(final Member member) {
        return memberRepository.save(member).getId();
    }

    private void 사용자_정보_조회를_검증하다(final ExtractableResponse<Response> response, final Member member) {
        final var expected = MemberProfileResponse.toResponse(member);
        final var expectedNickname = expected.getNickname();
        final var expectedProfileImage = expected.getProfileImage();

        final var actualNickname = response.jsonPath().getString("nickname");
        final var actualProfileImage = response.jsonPath().getString("profileImage");

        assertSoftly(softAssertions -> {
            softAssertions.assertThat(actualNickname).isEqualTo(expectedNickname);
            softAssertions.assertThat(actualProfileImage).isEqualTo(expectedProfileImage);
        });
    }
}
