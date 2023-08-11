package com.funeat.acceptance.common;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class CommonSteps {

    private static final String LOCATION = "Location";
    public static final HttpStatus 정상_처리 = HttpStatus.OK;
    public static final HttpStatus 정상_생성 = HttpStatus.CREATED;
    public static final HttpStatus 정상_처리_NO_CONTENT = HttpStatus.NO_CONTENT;
    public static final HttpStatus 리다이렉션_영구_이동 = HttpStatus.FOUND;
    public static final HttpStatus 승인되지_않음 = HttpStatus.UNAUTHORIZED;
    public static final HttpStatus 잘못된_요청 = HttpStatus.BAD_REQUEST;

    public static Long LOCATION_헤더에서_ID_추출(final ExtractableResponse<Response> response) {
        return Long.parseLong(response.header(LOCATION).split("/")[2]);
    }

    public static String LOCATION_헤더에서_리다이렉트_주소_추출(final ExtractableResponse<Response> response) {
        return response.header(LOCATION);
    }

    public static void LOCATION_헤더를_검증한다(final ExtractableResponse<Response> response) {
        assertThat(response.header("Location")).isNotBlank();
    }

    public static void STATUS_CODE를_검증한다(final ExtractableResponse<Response> response, final HttpStatus httpStatus) {
        assertThat(response.statusCode()).isEqualTo(httpStatus.value());
    }

    public static void REDIRECT_URL을_검증한다(final ExtractableResponse<Response> response, final String expected) {
        final var actual = LOCATION_헤더에서_리다이렉트_주소_추출(response);

        assertThat(actual).isEqualTo(expected);
    }
}
