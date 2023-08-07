package com.funeat.acceptance.product;

import static io.restassured.RestAssured.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class ProductSteps {

    public static ExtractableResponse<Response> 카테고리별_상품_목록_조회_요청(final Long categoryId, final String sortType,
                                                                  final String sortOrderType,
                                                                  final int page) {
        return given()
                .queryParam("sort", sortType + "," + sortOrderType)
                .queryParam("page", page)
                .when()
                .get("/api/categories/{category_id}/products", categoryId)
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_상세_조회_요청(final Long productId) {
        return given()
                .when()
                .get("/api/products/{product_id}", productId)
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_랭킹_조회_요청() {
        return given()
                .when()
                .get("/api/ranks/products")
                .then()
                .extract();
    }
}
