package com.funeat.acceptance.review;

import static io.restassured.RestAssured.given;

import com.funeat.review.dto.ReviewCreateRequest;
import com.funeat.review.dto.ReviewFavoriteRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import java.util.Objects;

@SuppressWarnings("NonAsciiCharacters")
public class ReviewSteps {

    public static ExtractableResponse<Response> 단일_리뷰_요청(final Long productId, final MultiPartSpecification image,
                                                         final ReviewCreateRequest request, final String loginCookie) {
        final var requestSpec = given()
                .cookie("FUNEAT", loginCookie);

        if (Objects.nonNull(image)) {
            requestSpec.multiPart(image);
        }

        return requestSpec
                .multiPart("reviewRequest", request, "application/json")
                .when()
                .post("/api/products/{productId}/reviews", productId)
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰_좋아요_요청(final Long productId, final Long reviewId,
                                                          final ReviewFavoriteRequest request,
                                                          final String loginCookie) {
        return given()
                .cookie("FUNEAT", loginCookie)
                .contentType("application/json")
                .body(request)
                .when()
                .patch("/api/products/{productId}/reviews/{reviewId}", productId, reviewId)
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 정렬된_리뷰_목록_조회_요청(final String loginCookie, final Long productId,
                                                                final String sort, final Integer page) {
        return given()
                .cookie("FUNEAT", loginCookie)
                .queryParam("sort", sort)
                .queryParam("page", page)
                .when()
                .get("/api/products/{product_id}/reviews", productId)
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰_랭킹_조회_요청() {
        return given()
                .when()
                .get("/api/ranks/reviews")
                .then()
                .extract();
    }
}
