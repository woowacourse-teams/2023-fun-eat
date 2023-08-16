package com.funeat.acceptance.member;

import static io.restassured.RestAssured.given;

import com.funeat.member.dto.MemberRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;

@SuppressWarnings("NonAsciiCharacters")
public class MemberSteps {

    public static ExtractableResponse<Response> 사용자_정보_조회_요청(final String loginCookie) {
        return given()
                .cookie("JSESSIONID", loginCookie)
                .when()
                .get("/api/members")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 사용자_정보_수정_요청(final String loginCookie,
                                                             final MultiPartSpecification image,
                                                             final MemberRequest request) {
        return given()
                .cookie("JSESSIONID", loginCookie)
                .multiPart(image)
                .multiPart("request", request, "application/json")
                .body(request)
                .when()
                .put("/api/members")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 사용자_리뷰_조회_요청(final String loginCookie, final String sort,
                                                             final Integer page) {
        return given()
                .when()
                .cookie("JSESSIONID", loginCookie)
                .queryParam("sort", sort)
                .queryParam("page", page)
                .get("/api/members/reviews")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 사용자_꿀조합_조회_요청(final String loginCookie, final String sort,
                                                              final Integer page) {
        return given()
                .when()
                .cookie("JSESSIONID", loginCookie)
                .queryParam("sort", sort)
                .queryParam("page", page)
                .get("/api/members/recipes")
                .then()
                .extract();
    }
}
