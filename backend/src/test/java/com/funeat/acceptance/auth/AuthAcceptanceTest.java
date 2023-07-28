package com.funeat.acceptance.auth;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.auth.application.AuthService;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Autowired
    private AuthService authService;

    @Test
    void 유저가_카카오_로그인_버튼을_누르면_카카오_로그인_페이지로_리다이렉트할_수_있다() {
        // given
        final var response = 카카오_로그인_버튼_클릭();
        final var expected = authService.getLoginRedirectUri();

        // when
        final String actual = response.header("Location");

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private ExtractableResponse<Response> 카카오_로그인_버튼_클릭() {
        return given()
                .redirects().follow(false)
                .when()
                .get("/api/auth/kakao")
                .then()
                .extract();
    }
}
