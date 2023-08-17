package com.funeat.recipe.persistence;

import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버3_생성;
import static com.funeat.fixture.PageFixture.페이지요청_기본_생성;
import static com.funeat.fixture.PageFixture.페이지요청_생성_시간_내림차순_생성;
import static com.funeat.fixture.PageFixture.페이지요청_생성_시간_오름차순_생성;
import static com.funeat.fixture.PageFixture.페이지요청_좋아요_내림차순_생성;
import static com.funeat.fixture.PageFixture.페이지요청_기본_생성;
import static com.funeat.fixture.ProductFixture.레시피_안에_들어가는_상품_생성;
import static com.funeat.fixture.ProductFixture.상품_망고빙수_가격5000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_애플망고_가격3000원_평점5점_생성;
import static com.funeat.fixture.RecipeFixture.레시피_생성;
import static com.funeat.fixture.RecipeFixture.레시피이미지_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.RepositoryTest;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class RecipeRepositoryTest extends RepositoryTest {

    @Nested
    class findAllByProductNameContaining_성공_테스트 {

        @Test
        void 상품명에_검색어가_포함된_상품이_있는_레시피들을_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            final var product3 = 상품_애플망고_가격3000원_평점5점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var recipe1 = 레시피_생성(member);
            final var recipe2 = 레시피_생성(member);
            복수_레시피_저장(recipe1, recipe2);

            final var productRecipe1 = 레시피_안에_들어가는_상품_생성(product1, recipe1);
            final var productRecipe2 = 레시피_안에_들어가는_상품_생성(product2, recipe1);
            final var productRecipe3 = 레시피_안에_들어가는_상품_생성(product3, recipe2);
            복수_레시피_상품_저장(productRecipe1, productRecipe2, productRecipe3);

            final var image1 = 레시피이미지_생성(recipe1);
            final var image2 = 레시피이미지_생성(recipe2);
            복수_레시피_이미지_저장(image1, image2);

            final var page = 페이지요청_기본_생성(0, 10);
            final var expected = List.of(recipe1, recipe2);

            // when
            final var actual = recipeRepository.findAllByProductNameContaining("망고", page).getContent();

            // then
            assertThat(actual).usingRecursiveComparison()
              .isEqualTo(expected);
        }
    }

    @Nested
    class findAllRecipes_성공_테스트 {

        @Test
        void 꿀조합을_좋아요가_많은_순으로_정렬한다() {
            // given
            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            final var member3 = 멤버_멤버3_생성();
            복수_멤버_저장(member1, member2, member3);

            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var recipe1_1 = 레시피_생성(member1, 1L);
            final var recipe1_2 = 레시피_생성(member1, 3L);
            final var recipe1_3 = 레시피_생성(member1, 2L);
            복수_꿀조합_저장(recipe1_1, recipe1_2, recipe1_3);

            final var product_recipe_1_1_1 = 레시피_안에_들어가는_상품_생성(product1, recipe1_1);
            final var product_recipe_1_1_2 = 레시피_안에_들어가는_상품_생성(product2, recipe1_1);
            final var product_recipe_1_1_3 = 레시피_안에_들어가는_상품_생성(product3, recipe1_1);
            final var product_recipe_1_2_1 = 레시피_안에_들어가는_상품_생성(product1, recipe1_2);
            final var product_recipe_1_2_2 = 레시피_안에_들어가는_상품_생성(product3, recipe1_2);
            복수_꿀조합_상품_저장(product_recipe_1_1_1, product_recipe_1_1_2, product_recipe_1_1_3, product_recipe_1_2_1,
                    product_recipe_1_2_2);

            final var recipeImage1_1 = 레시피이미지_생성(recipe1_1);
            final var recipeImage1_2 = 레시피이미지_생성(recipe1_2);
            복수_꿀조합_이미지_저장(recipeImage1_1, recipeImage1_2);

            final var page = 페이지요청_좋아요_내림차순_생성(0, 10);
            final var expected = List.of(recipe1_2, recipe1_3, recipe1_1);

            // when
            final var actual = recipeRepository.findAll(page).getContent();

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 꿀조합을_최신순으로_정렬한다() {
            // given
            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            final var member3 = 멤버_멤버3_생성();
            복수_멤버_저장(member1, member2, member3);

            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var recipe1_1 = 레시피_생성(member1, 1L);
            final var recipe1_2 = 레시피_생성(member1, 3L);
            final var recipe1_3 = 레시피_생성(member1, 2L);
            복수_꿀조합_저장(recipe1_1, recipe1_2, recipe1_3);

            final var product_recipe_1_1_1 = 레시피_안에_들어가는_상품_생성(product1, recipe1_1);
            final var product_recipe_1_1_2 = 레시피_안에_들어가는_상품_생성(product2, recipe1_1);
            final var product_recipe_1_1_3 = 레시피_안에_들어가는_상품_생성(product3, recipe1_1);
            final var product_recipe_1_2_1 = 레시피_안에_들어가는_상품_생성(product1, recipe1_2);
            final var product_recipe_1_2_2 = 레시피_안에_들어가는_상품_생성(product3, recipe1_2);
            복수_꿀조합_상품_저장(product_recipe_1_1_1, product_recipe_1_1_2, product_recipe_1_1_3, product_recipe_1_2_1,
                    product_recipe_1_2_2);

            final var recipeImage1_1 = 레시피이미지_생성(recipe1_1);
            final var recipeImage1_2 = 레시피이미지_생성(recipe1_2);
            복수_꿀조합_이미지_저장(recipeImage1_1, recipeImage1_2);

            final var page = 페이지요청_생성_시간_내림차순_생성(0, 10);
            final var expected = List.of(recipe1_3, recipe1_2, recipe1_1);

            // when
            final var actual = recipeRepository.findAll(page).getContent();

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 꿀조합을_오래된순으로_정렬한다() {
            // given
            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            final var member3 = 멤버_멤버3_생성();
            복수_멤버_저장(member1, member2, member3);

            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var recipe1_1 = 레시피_생성(member1, 1L);
            final var recipe1_2 = 레시피_생성(member1, 3L);
            final var recipe1_3 = 레시피_생성(member1, 2L);
            복수_꿀조합_저장(recipe1_1, recipe1_2, recipe1_3);

            final var product_recipe_1_1_1 = 레시피_안에_들어가는_상품_생성(product1, recipe1_1);
            final var product_recipe_1_1_2 = 레시피_안에_들어가는_상품_생성(product2, recipe1_1);
            final var product_recipe_1_1_3 = 레시피_안에_들어가는_상품_생성(product3, recipe1_1);
            final var product_recipe_1_2_1 = 레시피_안에_들어가는_상품_생성(product1, recipe1_2);
            final var product_recipe_1_2_2 = 레시피_안에_들어가는_상품_생성(product3, recipe1_2);
            복수_꿀조합_상품_저장(product_recipe_1_1_1, product_recipe_1_1_2, product_recipe_1_1_3, product_recipe_1_2_1,
                    product_recipe_1_2_2);

            final var recipeImage1_1 = 레시피이미지_생성(recipe1_1);
            final var recipeImage1_2 = 레시피이미지_생성(recipe1_2);
            복수_꿀조합_이미지_저장(recipeImage1_1, recipeImage1_2);

            final var page = 페이지요청_생성_시간_오름차순_생성(0, 10);
            final var expected = List.of(recipe1_1, recipe1_2, recipe1_3);

            // when
            final var actual = recipeRepository.findAll(page).getContent();

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class findRecipesByOrderByFavoriteCountDesc_성공_테스트 {

        @Test
        void 좋아요순으로_상위_3개의_레시피들을_조회한다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var recipe1 = 레시피_생성(member, 1L);
            final var recipe2 = 레시피_생성(member, 2L);
            final var recipe3 = 레시피_생성(member, 3L);
            final var recipe4 = 레시피_생성(member, 4L);
            복수_꿀조합_저장(recipe1, recipe2, recipe3, recipe4);

            final var page = 페이지요청_기본_생성(0, 3);
            final var expected = List.of(recipe4, recipe3, recipe2);

            // when
            final var actual = recipeRepository.findRecipesByOrderByFavoriteCountDesc(page);

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }
}
