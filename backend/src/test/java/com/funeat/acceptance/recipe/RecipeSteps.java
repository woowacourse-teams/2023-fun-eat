package com.funeat.acceptance.recipe;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키_획득;
import static com.funeat.fixture.RecipeFixture.레시피좋아요요청_생성;
import static io.restassured.RestAssured.given;

import com.funeat.recipe.dto.RecipeCommentCondition;
import com.funeat.recipe.dto.RecipeCommentCreateRequest;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeFavoriteRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("NonAsciiCharacters")
public class RecipeSteps {

    public static ExtractableResponse<Response> 레시피_작성_요청(final String loginCookie,
                                                          final List<MultiPartSpecification> images,
                                                          final RecipeCreateRequest recipeRequest) {
        final var requestSpec = given()
                .cookie("JSESSIONID", loginCookie);

        if (Objects.nonNull(images) && !images.isEmpty()) {
            images.forEach(requestSpec::multiPart);
        }

        return requestSpec
                .multiPart("recipeRequest", recipeRequest, "application/json")
                .when()
                .post("/api/recipes")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 레시피_상세_정보_요청(final String loginCookie, final Long recipeId) {
        return given()
                .cookie("JSESSIONID", loginCookie)
                .when()
                .get("/api/recipes/{recipeId}", recipeId)
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 레시피_목록_요청(final String sort, final Long page) {
        return given()
                .queryParam("sort", sort)
                .queryParam("page", page)
                .when()
                .get("/api/recipes")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 레시피_좋아요_요청(final String loginCookie, final Long recipeId,
                                                           final RecipeFavoriteRequest request) {
        return given()
                .cookie("JSESSIONID", loginCookie)
                .contentType("application/json")
                .body(request)
                .when()
                .patch("/api/recipes/{recipeId}", recipeId)
                .then()
                .extract();
    }

    public static void 여러명이_레시피_좋아요_요청(final List<Long> memberIds, final Long recipeId,
                                       final Boolean favorite) {
        final var request = 레시피좋아요요청_생성(favorite);

        for (final var memberId : memberIds) {
            레시피_좋아요_요청(로그인_쿠키_획득(memberId), recipeId, request);
        }
    }

    public static ExtractableResponse<Response> 레시피_랭킹_조회_요청() {
        return given()
                .when()
                .get("/api/ranks/recipes")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 레시피_검색_결과_조회_요청(final String query, final Long page) {
        return given()
                .queryParam("query", query)
                .queryParam("page", page)
                .when()
                .get("/api/search/recipes/results")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 레시피_댓글_작성_요청(final String loginCookie,
                                                             final Long recipeId,
                                                             final RecipeCommentCreateRequest request) {
        return given()
                .cookie("JSESSIONID", loginCookie)
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/recipes/" + recipeId + "/comments")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 레시피_댓글_조회_요청(final String loginCookie, final Long recipeId,
                                                             final RecipeCommentCondition condition) {
        return given()
                .cookie("JSESSIONID", loginCookie)
                .contentType("application/json")
                .param("lastId", condition.getLastId())
                .param("totalElements", condition.getTotalElements())
                .when()
                .get("/api/recipes/" + recipeId + "/comments")
                .then()
                .extract();
    }
}
