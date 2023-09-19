package com.funeat.acceptance.auth;

import static com.funeat.acceptance.auth.LoginSteps.로그아웃_요청;
import static com.funeat.acceptance.auth.LoginSteps.로그인_시도_요청;
import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키_획득;
import static com.funeat.acceptance.auth.LoginSteps.카카오_로그인_버튼_클릭;
import static com.funeat.acceptance.common.CommonSteps.LOCATION_헤더에서_리다이렉트_주소_추출;
import static com.funeat.acceptance.common.CommonSteps.REDIRECT_URL을_검증한다;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.리다이렉션_영구_이동;
import static com.funeat.acceptance.common.CommonSteps.인증되지_않음;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.fixture.MemberFixture.멤버1;
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

    private static final String 마이페이지 = "/members";
    private static final String 메인페이지 = "/";

    @Autowired
    private AuthService authService;

    @Nested
    class login_성공_테스트 {

        @Test
        void 멤버가_로그인_버튼을_누르면_OAUTH_로그인_페이지로_리다이렉트할_수_있다() {
            // given
            final var OAUTH_로그인_페이지 = authService.getLoginRedirectUri();

            // when
            final var 응답 = 카카오_로그인_버튼_클릭();

            // then
            STATUS_CODE를_검증한다(응답, 리다이렉션_영구_이동);
            REDIRECT_URL을_검증한다(응답, OAUTH_로그인_페이지);
        }
    }

    @Nested
    class loginAuthorizeUser_성공_테스트 {

        @Test
        void 신규_유저라면_마이페이지_경로를_헤더에_담아_응답을_보낸다() {
            // given && when
            final var 응답 = 로그인_시도_요청(멤버1);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            헤더에_리다이렉트가_존재하는지_검증한다(응답, 마이페이지);
        }

        @Test
        void 기존_유저라면_메인페이지_경로를_헤더에_담아_응답을_보낸다() {
            // given
            로그인_쿠키_획득(멤버1);

            // when
            final var 응답 = 로그인_시도_요청(멤버1);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            헤더에_리다이렉트가_존재하는지_검증한다(응답, 메인페이지);
        }
    }

    @Nested
    class logout_성공_테스트 {

        @Test
        void 로그아웃을_하다() {
            // given && when
            final var 응답 = 로그아웃_요청(로그인_쿠키_획득(멤버1));

            // then
            STATUS_CODE를_검증한다(응답, 리다이렉션_영구_이동);
            REDIRECT_URL을_검증한다(응답, 메인페이지);
        }
    }

    @Nested
    class logout_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 쿠키가_존재하지_않을_때_로그아웃을_하면_예외가_발생한다(final String cookie) {
            // given & when
            final var 응답 = 로그아웃_요청(cookie);

            // then
            STATUS_CODE를_검증한다(응답, 인증되지_않음);
        }
    }

    private void 헤더에_리다이렉트가_존재하는지_검증한다(final ExtractableResponse<Response> response, final String expected) {
        final var actual = LOCATION_헤더에서_리다이렉트_주소_추출(response);

        assertThat(actual).isEqualTo(expected);
    }
}
