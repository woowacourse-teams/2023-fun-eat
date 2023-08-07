package com.funeat.product.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.RepositoryTest;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class CategoryRepositoryTest extends RepositoryTest {

    @Nested
    class findAllByType_테스트 {

        @Test
        void 카테고리_타입이_FOOD인_모든_카테고리를_조회한다() {
            // given
            final var 간편식사 = new Category("간편식사", CategoryType.FOOD);
            final var 즉석조리 = new Category("즉석조리", CategoryType.FOOD);
            final var 과자류 = new Category("과자류", CategoryType.FOOD);
            final var 아이스크림 = new Category("아이스크림", CategoryType.FOOD);
            final var 식품 = new Category("식품", CategoryType.FOOD);
            final var 음료 = new Category("음료", CategoryType.FOOD);
            final var CU = new Category("CU", CategoryType.STORE);
            final var GS25 = new Category("GS25", CategoryType.STORE);
            final var EMART24 = new Category("EMART24", CategoryType.STORE);
            final var categories = List.of(간편식사, 즉석조리, 과자류, 아이스크림, 식품, 음료, CU, GS25, EMART24);
            복수_카테고리_저장(categories);
            final var expected = List.of(간편식사, 즉석조리, 과자류, 아이스크림, 식품, 음료);

            // when
            final var actual = categoryRepository.findAllByType(CategoryType.FOOD);

            // then
            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }

        @Test
        void 카테고리_타입이_STORE인_모든_카테고리를_조회한다() {
            // given
            final var 간편식사 = new Category("간편식사", CategoryType.FOOD);
            final var 즉석조리 = new Category("즉석조리", CategoryType.FOOD);
            final var 과자류 = new Category("과자류", CategoryType.FOOD);
            final var 아이스크림 = new Category("아이스크림", CategoryType.FOOD);
            final var 식품 = new Category("식품", CategoryType.FOOD);
            final var 음료 = new Category("음료", CategoryType.FOOD);
            final var CU = new Category("CU", CategoryType.STORE);
            final var GS25 = new Category("GS25", CategoryType.STORE);
            final var EMART24 = new Category("EMART24", CategoryType.STORE);
            복수_카테고리_저장(List.of(간편식사, 즉석조리, 과자류, 아이스크림, 식품, 음료, CU, GS25, EMART24));
            final var expected = List.of(CU, GS25, EMART24);

            // when
            final var actual = categoryRepository.findAllByType(CategoryType.STORE);

            // then
            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }
    }

    private void 복수_카테고리_저장(final List<Category> categories) {
        categoryRepository.saveAll(categories);
    }
}
