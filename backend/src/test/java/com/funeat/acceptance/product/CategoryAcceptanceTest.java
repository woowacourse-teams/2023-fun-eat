package com.funeat.acceptance.product;

import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.product.CategorySteps.공통_상품_카테고리_목록_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.dto.CategoryResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class CategoryAcceptanceTest extends AcceptanceTest {

    @Test
    void 공통_상품_카테고리의_목록을_조회한다() {
        // given
        final var 간편식사 = new Category("간편식사", CategoryType.FOOD);
        final var 즉석조리 = new Category("즉석조리", CategoryType.FOOD);
        final var 과자류 = new Category("과자류", CategoryType.FOOD);
        final var CU = new Category("CU", CategoryType.STORE);
        final var categories = List.of(간편식사, 즉석조리, 과자류, CU);
        카테고리_복수_추가_요청(categories);

        // when
        final var response = 공통_상품_카테고리_목록_조회_요청();

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
        공통_상품_카테고리_목록_조회_결과를_검증한다(response, List.of(간편식사, 즉석조리, 과자류));
    }

    private void 카테고리_복수_추가_요청(final List<Category> categories) {
        categoryRepository.saveAll(categories);
    }

    private void 공통_상품_카테고리_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response,
                                           final List<Category> categories) {
        final List<CategoryResponse> expected = new ArrayList<>();
        for (final var category : categories) {
            expected.add(CategoryResponse.toResponse(category));
        }

        final var actualResponses = response.jsonPath().getList("", CategoryResponse.class);
        assertThat(actualResponses).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }
}
