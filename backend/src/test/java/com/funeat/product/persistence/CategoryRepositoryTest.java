package com.funeat.product.persistence;

import static com.funeat.fixture.CategoryFixture.카테고리_CU_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_EMART24_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_GS25_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_과자류_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_세븐일레븐_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_식품_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_아이스크림_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_음료_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.RepositoryTest;
import com.funeat.product.domain.CategoryType;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class CategoryRepositoryTest extends RepositoryTest {

    @Nested
    class findAllByType_성공_테스트 {

        @Test
        void 카테고리_타입이_FOOD인_모든_카테고리를_조회한다() {
            // given
            final var 간편식사 = 카테고리_간편식사_생성();
            final var 즉석조리 = 카테고리_즉석조리_생성();
            final var 과자류 = 카테고리_과자류_생성();
            final var 아이스크림 = 카테고리_아이스크림_생성();
            final var 식품 = 카테고리_식품_생성();
            final var 음료 = 카테고리_음료_생성();
            final var CU = 카테고리_CU_생성();
            final var GS25 = 카테고리_GS25_생성();
            final var EMART24 = 카테고리_EMART24_생성();
            final var 세븐일레븐 = 카테고리_세븐일레븐_생성();
            복수_카테고리_저장(간편식사, 즉석조리, 과자류, 아이스크림, 식품, 음료, CU, GS25, EMART24, 세븐일레븐);

            final var expected = List.of(간편식사, 즉석조리, 과자류, 아이스크림, 식품, 음료);

            // when
            final var actual = categoryRepository.findAllByType(CategoryType.FOOD);

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 카테고리_타입이_STORE인_모든_카테고리를_조회한다() {
            // given
            final var 간편식사 = 카테고리_간편식사_생성();
            final var 즉석조리 = 카테고리_즉석조리_생성();
            final var 과자류 = 카테고리_과자류_생성();
            final var 아이스크림 = 카테고리_아이스크림_생성();
            final var 식품 = 카테고리_식품_생성();
            final var 음료 = 카테고리_음료_생성();
            final var CU = 카테고리_CU_생성();
            final var GS25 = 카테고리_GS25_생성();
            final var EMART24 = 카테고리_EMART24_생성();
            final var 세븐일레븐 = 카테고리_세븐일레븐_생성();
            복수_카테고리_저장(간편식사, 즉석조리, 과자류, 아이스크림, 식품, 음료, CU, GS25, EMART24, 세븐일레븐);

            final var expected = List.of(CU, GS25, EMART24, 세븐일레븐);

            // when
            final var actual = categoryRepository.findAllByType(CategoryType.STORE);

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }
}
