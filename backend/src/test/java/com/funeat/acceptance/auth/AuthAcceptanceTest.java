package com.funeat.acceptance.auth;

import static com.funeat.acceptance.auth.LoginSteps.로그아웃_요청;
import static com.funeat.acceptance.auth.LoginSteps.로그인_시도_요청;
import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키를_얻는다;
import static com.funeat.acceptance.auth.LoginSteps.카카오_로그인_버튼_클릭;
import static com.funeat.acceptance.common.CommonSteps.LOCATION_헤더에서_리다이렉트_주소_추출;
import static com.funeat.acceptance.common.CommonSteps.REDIRECT_URL을_검증한다;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.리다이렉션_영구_이동;
import static com.funeat.acceptance.common.CommonSteps.인증되지_않음;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.auth.application.AuthService;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Autowired
    private AuthService authService;

    @Nested
    class kakaoLogin_성공_테스트 {

        @Test
        void 멤버가_카카오_로그인_버튼을_누르면_카카오_로그인_페이지로_리다이렉트할_수_있다() {
            // given
            final var expected = authService.getLoginRedirectUri();

            // when
            final var response = 카카오_로그인_버튼_클릭();

            // then
            STATUS_CODE를_검증한다(response, 리다이렉션_영구_이동);
            REDIRECT_URL을_검증한다(response, expected);
        }
    }

    @Nested
    class loginAuthorizeUser_성공_테스트 {

        @Test
        void 신규_유저라면_마이페이지_경로를_헤더에_담아_응답을_보낸다() {
            // given
            final var code = "member1";
            final var loginCookie = "12345";

            // when
            final var response = 로그인_시도_요청(code, loginCookie);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            헤더에_리다이렉트가_존재하는지_검증한다(response, "/members");
        }

        @Test
        void 기존_유저라면_메인페이지_경로를_헤더에_담아_응답을_보낸다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var code = "member1";
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 로그인_시도_요청(code, loginCookie);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            헤더에_리다이렉트가_존재하는지_검증한다(response, "/");
        }
    }

    @Nested
    class logout_성공_테스트 {

        @Test
        void 로그아웃을_하다() {
            // given
            final var loginCookie = 로그인_쿠키를_얻는다();
            final var expected = "/";

            // when
            final var response = 로그아웃_요청(loginCookie);

            // then
            STATUS_CODE를_검증한다(response, 리다이렉션_영구_이동);
            REDIRECT_URL을_검증한다(response, expected);
        }
    }

    @Nested
    class logout_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 쿠키가_존재하지_않을_때_로그아웃을_하면_예외가_발생한다(final String cookie) {
            // given & when
            final var response = 로그아웃_요청(cookie);

            // then
            STATUS_CODE를_검증한다(response, 인증되지_않음);
        }
    }

    private void 헤더에_리다이렉트가_존재하는지_검증한다(final ExtractableResponse<Response> response, final String expected) {
        final var actual = LOCATION_헤더에서_리다이렉트_주소_추출(response);

        assertThat(actual).isEqualTo(expected);
    }
}
