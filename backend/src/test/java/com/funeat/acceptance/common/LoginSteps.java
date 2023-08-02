package com.funeat.acceptance.common;

import io.restassured.RestAssured;

@SuppressWarnings("NonAsciiCharacters")
public class LoginSteps {

    public static String 로그인_쿠키를_얻는다() {
        return RestAssured.given()
                .queryParam("code", "test")
                .when()
                .get("/api/login/oauth2/code/kakao")
                .then()
                .extract()
                .response()
                .getCookie("JSESSIONID");
    }
}
