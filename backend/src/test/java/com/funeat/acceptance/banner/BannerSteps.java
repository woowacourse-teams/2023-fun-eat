package com.funeat.acceptance.banner;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class BannerSteps {

    public static ExtractableResponse<Response> 배너_조회_요청() {
        return RestAssured.given()
                .when()
                .get("/api/banners")
                .then()
                .extract();
    }
}
