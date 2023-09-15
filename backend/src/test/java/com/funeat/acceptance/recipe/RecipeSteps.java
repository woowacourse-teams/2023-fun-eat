package com.funeat.acceptance.recipe;

import static io.restassured.RestAssured.given;

import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeFavoriteRequest;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("NonAsciiCharacters")
public class RecipeSteps {

    public static ExtractableResponse<Response> 레시피_작성_요청(final String loginCookie,
                                                          final List<MultiPartSpecification> images,
                                                          final RecipeCreateRequest recipeRequest) {
        final var requestSpec = given()
                .cookie("FUNEAT", loginCookie);

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
                .cookie("FUNEAT", loginCookie)
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
                .cookie("FUNEAT", loginCookie)
                .contentType("application/json")
                .body(request)
                .when()
                .patch("/api/recipes/{recipeId}", recipeId)
                .then()
                .extract();
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
}
