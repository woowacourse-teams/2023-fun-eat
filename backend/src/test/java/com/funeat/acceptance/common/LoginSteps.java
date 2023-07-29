package com.funeat.acceptance.common;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class LoginSteps {

    public static Cookies 로그인_쿠키를_얻는다() {
        final ExtractableResponse<Response> response = RestAssured.given()
                .queryParam("code", "test")
                .when()
                .get("/login/oauth2/code/kakao")
                .then()
                .extract();

        return response.detailedCookies();
    }
}
