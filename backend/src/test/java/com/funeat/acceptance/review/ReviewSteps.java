package com.funeat.acceptance.review;

import static io.restassured.RestAssured.given;

import com.funeat.review.presentation.dto.ReviewCreateRequest;
import com.funeat.review.presentation.dto.ReviewFavoriteRequest;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;

@SuppressWarnings("NonAsciiCharacters")
public class ReviewSteps {

    public static ExtractableResponse<Response> 리뷰_추가_요청(final Long productId, final MultiPartSpecification image,
                                                         final ReviewCreateRequest request, final String loginCookie) {
        return given()
                .cookie("JSESSIONID", loginCookie)
                .multiPart(image)
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
                .cookie("JSESSIONID", loginCookie)
                .contentType("application/json")
                .body(request)
                .when()
                .patch("/api/products/{productId}/reviews/{reviewId}", productId, reviewId)
                .then()
                .extract();
    }

    public static MultiPartSpecification 리뷰_사진_명세_요청() {
        return new MultiPartSpecBuilder("image".getBytes())
                .fileName("testImage.png")
                .controlName("image")
                .mimeType("image/png")
                .build();
    }
}
