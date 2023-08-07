package com.funeat.acceptance.member;

import static io.restassured.RestAssured.given;

import com.funeat.member.dto.MemberRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class MemberSteps {

    public static ExtractableResponse<Response> 사용자_정보_조회_요청(final String loginCookie) {
        return given()
                .cookie("JSESSIONID", loginCookie)
                .when()
                .get("/api/members")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 사용자_정보_수정_요청(final String loginCookie, final MemberRequest request) {
        return given()
                .cookie("JSESSIONID", loginCookie)
                .contentType("application/json")
                .body(request)
                .when()
                .put("/api/members")
                .then()
                .extract();
    }
}
