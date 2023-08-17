package com.funeat.product.persistence;

import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.ProductFixture.레시피_안에_들어가는_상품_생성;
import static com.funeat.fixture.ProductFixture.상품_망고빙수_가격5000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_애플망고_가격3000원_평점5점_생성;
import static com.funeat.fixture.RecipeFixture.레시피_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.RepositoryTest;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ProductRecipeRepositoryTest extends RepositoryTest {

    @Nested
    class findProductByRecipe_성공_테스트 {

        @Test
        void 레시피에_사용된_상품들을_조회할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_애플망고_가격3000원_평점5점_생성(category);
            final var product2 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            복수_상품_저장(product1, product2);

            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var recipe = 레시피_생성(member, 1L);
            단일_꿀조합_저장(recipe);

            final var product_recipe_1 = 레시피_안에_들어가는_상품_생성(product1, recipe);
            final var product_recipe_2 = 레시피_안에_들어가는_상품_생성(product2, recipe);
            복수_꿀조합_상품_저장(product_recipe_1, product_recipe_2);

            final var expected = List.of(product1, product2);

            // when
            final var actual = productRecipeRepository.findProductByRecipe(recipe);

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }
}
