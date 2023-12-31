package com.funeat.acceptance.common;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.dto.PageDto;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class CommonSteps {

    private static final String LOCATION = "Location";
    public static final HttpStatus 정상_처리 = HttpStatus.OK;
    public static final HttpStatus 정상_생성 = HttpStatus.CREATED;
    public static final HttpStatus 정상_처리_NO_CONTENT = HttpStatus.NO_CONTENT;
    public static final HttpStatus 리다이렉션_영구_이동 = HttpStatus.FOUND;
    public static final HttpStatus 인증되지_않음 = HttpStatus.UNAUTHORIZED;
    public static final HttpStatus 잘못된_요청 = HttpStatus.BAD_REQUEST;
    public static final HttpStatus 찾을수_없음 = HttpStatus.NOT_FOUND;

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

    public static MultiPartSpecification 사진_명세_요청(final String name) {
        return new MultiPartSpecBuilder("image".getBytes())
                .fileName(String.format("%s.png", name))
                .controlName("image")
                .mimeType("image/png")
                .build();
    }

    public static List<MultiPartSpecification> 여러개_사진_명세_요청(final String... names) {
        final var images = new ArrayList<MultiPartSpecification>();

        for (final String name : names) {
            images.add(new MultiPartSpecBuilder("image".getBytes())
                    .fileName(String.format("%s.png", name))
                    .controlName("image")
                    .mimeType("image/png")
                    .build()
            );
        }

        return images;
    }

    public static void 페이지를_검증한다(final ExtractableResponse<Response> response, final PageDto expected) {
        final var actual = response.jsonPath().getObject("page", PageDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    public static void 다음_데이터가_있는지_검증한다(final ExtractableResponse<Response> response, final boolean expected) {
        final var actual = response.jsonPath().getBoolean("hasNext");

        assertThat(actual).isEqualTo(expected);
    }
}
