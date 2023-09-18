package com.funeat.acceptance.product;

import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.잘못된_요청;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.product.CategorySteps.카테고리_목록_조회_요청;
import static com.funeat.exception.CommonErrorCode.REQUEST_VALID_ERROR_CODE;
import static com.funeat.fixture.CategoryFixture.음식;
import static com.funeat.fixture.CategoryFixture.카테고리_CU_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_과자류_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.product.dto.CategoryResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
public class CategoryAcceptanceTest extends AcceptanceTest {


    @Nested
    class getAllCategoriesByType_성공_테스트 {

        @Test
        void 공통_상품_카테고리의_목록을_조회한다() {
            // given
            final var 간편식사 = 단일_카테고리_저장(카테고리_간편식사_생성());
            final var 즉석조리 = 단일_카테고리_저장(카테고리_즉석조리_생성());
            final var 과자류 = 단일_카테고리_저장(카테고리_과자류_생성());
            final var CU = 단일_카테고리_저장(카테고리_CU_생성());

            // when
            final var 응답 = 카테고리_목록_조회_요청(음식);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            공통_상품_카테고리_목록_조회_결과를_검증한다(응답, List.of(간편식사, 즉석조리, 과자류), List.of(CU));
        }
    }

    @Nested
    class getAllCategoriesByType_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"a", "foo"})
        void 존재하지_않는_카테고리의_목록을_조회할때_예외가_발생한다(final String type) {
            // given & when
            final var 응답 = 카테고리_목록_조회_요청(type);

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    REQUEST_VALID_ERROR_CODE.getMessage());
        }
    }

    private void 공통_상품_카테고리_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response,
                                           final List<Long> includeCategoryIds, final List<Long> excludeCategoryIds) {
        final var actual = response.jsonPath()
                .getList("", CategoryResponse.class);

        assertThat(actual).extracting(CategoryResponse::getId)
                .containsExactlyElementsOf(includeCategoryIds)
                .doesNotContainAnyElementsOf(excludeCategoryIds);
    }

    private void RESPONSE_CODE와_MESSAGE를_검증한다(final ExtractableResponse<Response> response, final String expectedCode,
                                              final String expectedMessage) {
        assertSoftly(soft -> {
            soft.assertThat(response.jsonPath().getString("code"))
                    .isEqualTo(expectedCode);
            soft.assertThat(response.jsonPath().getString("message"))
                    .isEqualTo(expectedMessage);
        });
    }
}
