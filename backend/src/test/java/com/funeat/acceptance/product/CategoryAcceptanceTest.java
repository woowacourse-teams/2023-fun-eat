package com.funeat.acceptance.product;

import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.잘못된_요청;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.product.CategorySteps.카테고리_목록_조회_요청;
import static com.funeat.exception.CommonErrorCode.REQUEST_VALID_ERROR_CODE;
import static com.funeat.fixture.CategoryFixture.카테고리_CU_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_과자류_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.product.domain.Category;
import com.funeat.product.dto.CategoryResponse;
import com.funeat.review.dto.RankingReviewDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
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
            복수_카테고리_저장(카테고리_간편식사_생성(),
                    카테고리_즉석조리_생성(),
                    카테고리_과자류_생성(),
                    카테고리_CU_생성());

            // when
            final var response = 카테고리_목록_조회_요청("food");

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            공통_상품_카테고리_목록_조회_결과를_검증한다(response, List.of(1L, 2L, 3L));
        }
    }

    @Nested
    class getAllCategoriesByType_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"a", "foo"})
        void 존재하지_않는_카테고리의_목록을_조회할때_예외가_발생한다(final String type) {
            // given & when
            final var response = 카테고리_목록_조회_요청(type);

            // then
            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, REQUEST_VALID_ERROR_CODE.getCode(),
                    REQUEST_VALID_ERROR_CODE.getMessage());
        }
    }

    private void 공통_상품_카테고리_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response,
                                           final List<Long> categoryIds) {
        final var actual = response.jsonPath()
                .getList("", CategoryResponse.class);

        assertThat(actual).extracting(CategoryResponse::getId)
                .containsExactlyElementsOf(categoryIds);
    }

    private void RESPONSE_CODE와_MESSAGE를_검증한다(final ExtractableResponse<Response> response, final String expectedCode,
                                              final String expectedMessage) {
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.jsonPath().getString("code"))
                    .isEqualTo(expectedCode);
            softAssertions.assertThat(response.jsonPath().getString("message"))
                    .isEqualTo(expectedMessage);
        });
    }
}
