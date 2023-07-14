package com.funeat.acceptance.product;

import static io.restassured.RestAssured.given;

import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.domain.SortOrderType;
import com.funeat.product.domain.SortType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class ProductSteps {

    public static final Category 간편식사 = new Category("간편식사", CategoryType.FOOD);
    public static final Category 즉석조리 = new Category("즉석조리", CategoryType.FOOD);
    public static final Category 과자류 = new Category("과자류", CategoryType.FOOD);
    public static final Category 아이스크림 = new Category("아이스크림", CategoryType.FOOD);
    public static final Category 식품 = new Category("식품", CategoryType.FOOD);
    public static final Category 음료 = new Category("음료", CategoryType.FOOD);

    public static ExtractableResponse<Response> 카테고리_목록_조회_요청() {
        return given()
                .when()
                .get("/api/categories")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 카테고리별_상품_목록_조회_요청(final Long categoryId, final SortType sortType,
                                                                  final SortOrderType sortOrderType,
                                                                  final Integer page) {
        return given()
                .queryParam("sort", sortType.name().toLowerCase())
                .queryParam("order", sortOrderType.name().toLowerCase())
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
}
