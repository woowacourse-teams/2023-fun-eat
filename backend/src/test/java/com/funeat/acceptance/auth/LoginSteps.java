package com.funeat.acceptance.auth;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

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

    public static ExtractableResponse<Response> 카카오_로그인_버튼_클릭() {
        return given()
                .redirects().follow(false)
                .when()
                .get("/api/auth/kakao")
                .then()
                .extract();
    }
}
