package com.funeat.acceptance.product;

import static io.restassured.RestAssured.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class CategorySteps {

    public static ExtractableResponse<Response> 공통_상품_카테고리_목록_조회_요청() {
        return given()
                .queryParam("type", "food")
                .when()
                .get("/api/categories")
                .then()
                .extract();
    }
}
