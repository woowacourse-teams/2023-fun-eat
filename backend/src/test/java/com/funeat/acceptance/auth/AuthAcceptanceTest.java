package com.funeat.acceptance.auth;

import static com.funeat.acceptance.auth.LoginSteps.로그아웃_요청;
import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키를_얻는다;
import static com.funeat.acceptance.auth.LoginSteps.카카오_로그인_버튼_클릭;
import static com.funeat.acceptance.common.CommonSteps.REDIRECT_URL을_검증한다;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.리다이렉션_영구_이동;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.auth.application.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Autowired
    private AuthService authService;

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

    @Test
    void 로그아웃을_하다() {
        // given
        final var loginCookie = 로그인_쿠키를_얻는다();

        // when
        final var response = 로그아웃_요청(loginCookie);

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
    }
}
