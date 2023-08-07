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

            final List<Category> categories = List.of(간편식사, 즉석조리, 과자류, 아이스크림, 식품, 음료, CU, GS25, EMART24);
            for (Category category : categories) {
                System.out.println("@@@##" + category + " " + category.getName());
            }
            ;
            categoryRepository.saveAll(categories);
            System.out.println("어디서 그런거져");
            final var expected = List.of(간편식사, 즉석조리, 과자류, 아이스크림, 식품, 음료);

            // when
            System.out.println("업데이트 문이 어디서..??");
            final var actual = categoryRepository.findAllByType(CategoryType.FOOD);
            System.out.println("업데이트 문이 어디서..!!");
            // then
            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
            System.out.println("???");
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

            categoryRepository.saveAll(List.of(간편식사, 즉석조리, 과자류, 아이스크림, 식품, 음료, CU, GS25, EMART24));
            final var expected = List.of(CU, GS25, EMART24);
            // when
            final var actual = categoryRepository.findAllByType(CategoryType.STORE);

            // then
            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }
    }
}
