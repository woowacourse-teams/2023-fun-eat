package com.funeat.acceptance.recipe;

import static io.restassured.RestAssured.given;

import com.funeat.recipe.dto.RecipeCreateRequest;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RecipeSteps {

    public static ExtractableResponse<Response> 레시피_추가_요청(final RecipeCreateRequest recipeRequest,
                                                          final List<MultiPartSpecification> imageList,
                                                          final String loginCookie) {
        final var request = given()
                .cookie("JSESSIONID", loginCookie);
        imageList.forEach(request::multiPart);
        return request
                .multiPart("recipeRequest", recipeRequest, "application/json")
                .when()
                .post("/api/recipes")
                .then()
                .extract();
    }

    public static List<MultiPartSpecification> 여러_사진_요청(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new MultiPartSpecBuilder("image".getBytes())
                        .fileName("testImage.png")
                        .controlName("images")
                        .mimeType("image/png")
                        .build())
                .collect(Collectors.toList());
    }
}