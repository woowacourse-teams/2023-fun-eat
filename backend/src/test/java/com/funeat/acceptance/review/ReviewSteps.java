package com.funeat.acceptance.review;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키_획득;
import static com.funeat.acceptance.common.CommonSteps.LOCATION_헤더에서_ID_추출;
import static com.funeat.fixture.ReviewFixture.리뷰좋아요요청_생성;
import static io.restassured.RestAssured.given;

import com.funeat.review.dto.ReviewCreateRequest;
import com.funeat.review.dto.ReviewFavoriteRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("NonAsciiCharacters")
public class ReviewSteps {

    public static ExtractableResponse<Response> 리뷰_작성_요청(final String loginCookie, final Long productId,
                                                         final MultiPartSpecification image,
                                                         final ReviewCreateRequest request) {
        final var requestSpec = given()
                .cookie("JSESSIONID", loginCookie);

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

    public static ExtractableResponse<Response> 리뷰_좋아요_요청(final String loginCookie, final Long productId,
                                                          final Long reviewId, final ReviewFavoriteRequest request) {
        return given()
                .cookie("JSESSIONID", loginCookie)
                .contentType("application/json")
                .body(request)
                .when()
                .patch("/api/products/{productId}/reviews/{reviewId}", productId, reviewId)
                .then()
                .extract();
    }

    public static void 여러명이_리뷰_좋아요_요청(final List<Long> memberIds, final Long productId, final Long reviewId,
                                      final Boolean favorite) {
        final var request = 리뷰좋아요요청_생성(favorite);

        for (final var memberId : memberIds) {
            리뷰_좋아요_요청(로그인_쿠키_획득(memberId), productId, reviewId, request);
        }
    }

    public static ExtractableResponse<Response> 정렬된_리뷰_목록_조회_요청(final String loginCookie, final Long productId,
                                                                final String sort, final Long page) {
        return given()
                .cookie("JSESSIONID", loginCookie)
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

    public static ExtractableResponse<Response> 리뷰_삭제_요청(final String loginCookie,
                                                         final Long productId, final Long reviewId) {
        return given()
                .cookie("JSESSIONID", loginCookie)
                .when()
                .delete("/api/products/{productId}/reviews/{reviewId}", productId, reviewId)
                .then()
                .extract();
    }
}
