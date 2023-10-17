package com.funeat.acceptance.product;

import static io.restassured.RestAssured.given;


import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class ProductSteps {

    public static ExtractableResponse<Response> 카테고리별_상품_목록_조회_요청(final Long categoryId, final String sort,
                                                                  final Long lastProductId) {
        return given()
                .queryParam("sort", sort)
                .queryParam("lastProductId", lastProductId)
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

    public static ExtractableResponse<Response> 상품_자동_완성_검색_요청(final String query, final Long page) {
        return given()
                .queryParam("query", query)
                .queryParam("page", page)
                .when()
                .get("/api/search/products")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_검색_결과_조회_요청(final String query, final Long page) {
        return given()
                .queryParam("query", query)
                .queryParam("page", page)
                .when()
                .get("/api/search/products/results")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_레시피_목록_요청(final Long productId, final String sort,
                                                             final Long page) {
        return given()
                .queryParam("sort", sort)
                .queryParam("page", page)
                .when()
                .get("/api/products/{productId}/recipes", productId)
                .then()
                .extract();
    }
}
