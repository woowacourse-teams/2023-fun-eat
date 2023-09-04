package com.funeat.acceptance.recipe;

import static io.restassured.RestAssured.given;

import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeFavoriteRequest;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SuppressWarnings("NonAsciiCharacters")
public class RecipeSteps {

    public static ExtractableResponse<Response> 레시피_생성_요청(final RecipeCreateRequest recipeRequest,
                                                          final String loginCookie) {
        return given()
                .cookie("FUNEAT", loginCookie)
                .body(recipeRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/recipes")
                .then()
                .extract();
    }

    public static Long 레시피_추가_요청하고_id_반환(final RecipeCreateRequest recipeRequest, final String loginCookie) {
        final var response = 레시피_생성_요청(recipeRequest, loginCookie);
        return Long.parseLong(response.header("Location").split("/")[3]);
    }

    public static ExtractableResponse<Response> 레시피_상세_정보_요청(final String loginCookie, final Long recipeId) {
        return given()
                .cookie("FUNEAT", loginCookie)
                .when()
                .get("/api/recipes/{recipeId}", recipeId)
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 레시피_목록_요청(final String sortType, final String sortOrderType,
                                                          final int page) {
        return given()
                .queryParam("sort", sortType + "," + sortOrderType)
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

    public static List<MultiPartSpecification> 여러_사진_요청(final int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new MultiPartSpecBuilder("image".getBytes())
                        .fileName("testImage.png")
                        .controlName("images")
                        .mimeType("image/png")
                        .build())
                .collect(Collectors.toList());
    }

    public static ExtractableResponse<Response> 레시피_랭킹_조회_요청() {
        return given()
                .when()
                .get("/api/ranks/recipes")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 레시피_검색_결과_조회_요청(final String query, final int page) {
        return given()
                .queryParam("query", query)
                .queryParam("page", page)
                .when()
                .get("/api/search/recipes/results")
                .then()
                .extract();
    }
}
