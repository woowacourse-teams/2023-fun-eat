package com.funeat.acceptance.recipe;

import static io.restassured.RestAssured.given;

import com.funeat.recipe.dto.RecipeCreateRequest;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RecipeSteps {

    public static ExtractableResponse<Response> 레시피_생성_요청(final RecipeCreateRequest recipeRequest,
                                                          final List<MultiPartSpecification> images,
                                                          final String loginCookie) {
        final var request = given()
                .cookie("JSESSIONID", loginCookie);
        images.forEach(request::multiPart);
        return request
                .multiPart("recipeRequest", recipeRequest, "application/json")
                .when()
                .post("/api/recipes")
                .then()
                .extract();
    }

    public static Long 레시피_추가_요청하고_id_반환(final RecipeCreateRequest recipeRequest,
                                         final List<MultiPartSpecification> imageList,
                                         final String loginCookie) {
        final var response = 레시피_생성_요청(recipeRequest, imageList, loginCookie);
        return Long.parseLong(response.header("Location").split("/")[3]);
    }

    public static ExtractableResponse<Response> 레시피_상세_정보_요청(final String loginCookie, final Long recipeId) {
        return given()
                .cookie("JSESSIONID", loginCookie)
                .when()
                .get("/api/recipes/{recipeId}", recipeId)
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
}
