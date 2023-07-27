package com.funeat.acceptance.tag;

import static io.restassured.RestAssured.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class TagSteps {

    public static ExtractableResponse<Response> 전체_태그_목록_조회_요청() {
        return given()
                .when()
                .get("/api/tags")
                .then()
                .extract();
    }
}
