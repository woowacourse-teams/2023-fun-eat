package com.funeat.product.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.DataCleaner;
import com.funeat.common.DataClearExtension;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(DataCleaner.class)
@ExtendWith(DataClearExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class CategoryRepositoryTest {

    public static final Category 간편식사 = new Category("간편식사", CategoryType.FOOD);
    public static final Category 즉석조리 = new Category("즉석조리", CategoryType.FOOD);
    public static final Category 과자류 = new Category("과자류", CategoryType.FOOD);
    public static final Category 아이스크림 = new Category("아이스크림", CategoryType.FOOD);
    public static final Category 식품 = new Category("식품", CategoryType.FOOD);
    public static final Category 음료 = new Category("음료", CategoryType.FOOD);
    public static final Category CU = new Category("CU", CategoryType.STORE);
    public static final Category GS25 = new Category("GS25", CategoryType.STORE);
    public static final Category EMART24 = new Category("EMART24", CategoryType.STORE);

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void 카테고리_타입이_FOOD인_모든_카테고리를_조회한다() {
        // given
        categoryRepository.saveAll(List.of(간편식사, 즉석조리, 과자류, 아이스크림, 식품, 음료, CU, GS25, EMART24));

        // when
        final var actual = categoryRepository.findAllByType(CategoryType.FOOD);

        // then
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsOnly(간편식사, 즉석조리, 과자류, 아이스크림, 식품, 음료);
    }

    @Test
    void 카테고리_타입이_STORE인_모든_카테고리를_조회한다() {
        // given
        categoryRepository.saveAll(List.of(간편식사, 즉석조리, 과자류, 아이스크림, 식품, 음료, CU, GS25, EMART24));
        final var expected = List.of(CU, GS25, EMART24);

        // when
        final var actual = categoryRepository.findAllByType(CategoryType.STORE);

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
