package com.funeat.acceptance.member;

import static io.restassured.RestAssured.given;

import com.funeat.member.dto.MemberRequest;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class MemberSteps {

    public static ExtractableResponse<Response> 사용자_정보_조회_요청(final String loginCookie) {
        return given()
                .cookie("FUNEAT", loginCookie)
                .when()
                .get("/api/members")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 사용자_정보_수정_요청(final String loginCookie,
                                                             final MemberRequest memberRequest) {
        return given()
                .cookie("FUNEAT", loginCookie)
                .contentType(ContentType.JSON)
                .body(memberRequest)
                .log().all()
                .when()
                .put("/api/members")
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 사용자_리뷰_조회_요청(final String loginCookie, final String sort,
                                                             final Integer page) {
        return given()
                .when()
                .cookie("FUNEAT", loginCookie)
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
                .cookie("FUNEAT", loginCookie)
                .queryParam("sort", sort)
                .queryParam("page", page)
                .get("/api/members/recipes")
                .then()
                .extract();
    }
}
