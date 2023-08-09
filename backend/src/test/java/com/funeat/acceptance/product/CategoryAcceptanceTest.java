package com.funeat.acceptance.product;

import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.product.CategorySteps.공통_상품_카테고리_목록_조회_요청;
import static com.funeat.fixture.CategoryFixture.카테고리_CU_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_과자류_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.product.domain.Category;
import com.funeat.product.dto.CategoryResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class CategoryAcceptanceTest extends AcceptanceTest {

    @Test
    void 공통_상품_카테고리의_목록을_조회한다() {
        // given
        final var 간편식사 = 카테고리_간편식사_생성();
        final var 즉석조리 = 카테고리_즉석조리_생성();
        final var 과자류 = 카테고리_과자류_생성();
        final var CU = 카테고리_CU_생성();
        복수_카테고리_저장(간편식사, 즉석조리, 과자류, CU);

        // when
        final var response = 공통_상품_카테고리_목록_조회_요청();

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
        공통_상품_카테고리_목록_조회_결과를_검증한다(response, List.of(간편식사, 즉석조리, 과자류));
    }

    private void 공통_상품_카테고리_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response,
                                           final List<Category> categories) {
        final var expected = categories.stream()
                .map(CategoryResponse::toResponse)
                .collect(Collectors.toList());

        final var actual = response.jsonPath().getList("", CategoryResponse.class);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
