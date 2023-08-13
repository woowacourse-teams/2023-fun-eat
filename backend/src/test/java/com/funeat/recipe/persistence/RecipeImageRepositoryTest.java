package com.funeat.recipe.persistence;

import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.ProductFixture.레시피_안에_들어가는_상품_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점1점_생성;
import static com.funeat.fixture.RecipeFixture.레시피_생성;
import static com.funeat.fixture.RecipeFixture.레시피이미지_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.RepositoryTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class RecipeImageRepositoryTest extends RepositoryTest {

    @Nested
    class findByRecipe_성공_테스트 {

        @Test
        void 레시피에_사용된_이미지들을_조회할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var recipe = 레시피_생성(member);
            단일_레시피_저장(recipe);

            final var productRecipe1 = 레시피_안에_들어가는_상품_생성(product1, recipe);
            final var productRecipe2 = 레시피_안에_들어가는_상품_생성(product2, recipe);
            final var productRecipe3 = 레시피_안에_들어가는_상품_생성(product3, recipe);
            복수_레시피_상품_저장(productRecipe1, productRecipe2, productRecipe3);

            final var image1 = 레시피이미지_생성(recipe);
            final var image2 = 레시피이미지_생성(recipe);
            final var image3 = 레시피이미지_생성(recipe);
            복수_레시피_이미지_저장(image1, image2, image3);

            // when
            final var images = recipeImageRepository.findByRecipe(recipe);

            // then
            assertThat(images.size()).isEqualTo(3);
        }
    }
}
