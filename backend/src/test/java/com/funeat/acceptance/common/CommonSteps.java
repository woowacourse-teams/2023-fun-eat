package com.funeat.acceptance.common;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class CommonSteps {

    public static final HttpStatus 정상_처리 = HttpStatus.OK;
    public static final HttpStatus 정상_생성 = HttpStatus.CREATED;
    public static final HttpStatus 정상_처리_NO_CONTENT = HttpStatus.NO_CONTENT;

    public static Long LOCATION_헤더에서_ID_추출(final ExtractableResponse<Response> response) {
        return Long.parseLong(response.header("Location").split("/")[2]);
    }

    public static void LOCATION_헤더를_검증한다(final ExtractableResponse<Response> response) {
        assertThat(response.header("Location")).isNotBlank();
    }

    public static void STATUS_CODE를_검증한다(final ExtractableResponse<Response> response, final HttpStatus httpStatus) {
        assertThat(response.statusCode()).isEqualTo(httpStatus.value());
    }
}
