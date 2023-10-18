package com.funeat.recipe.persistence;

import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버3_생성;
import static com.funeat.fixture.PageFixture.과거순;
import static com.funeat.fixture.PageFixture.좋아요수_내림차순;
import static com.funeat.fixture.PageFixture.최신순;
import static com.funeat.fixture.PageFixture.페이지요청_기본_생성;
import static com.funeat.fixture.PageFixture.페이지요청_생성;
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
import java.util.Collections;
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

            final var page = 페이지요청_생성(0, 10, 좋아요수_내림차순);
            final var expected = List.of(recipe1_2, recipe1_3, recipe1_1);

            // when
            final var actual = recipeRepository.findAll(page).getContent();

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 꿀조합을_최신순으로_정렬한다() throws InterruptedException {
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
            Thread.sleep(100);
            final var recipe1_2 = 레시피_생성(member1, 3L);
            Thread.sleep(100);
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

            final var page = 페이지요청_생성(0, 10, 최신순);
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

            final var page = 페이지요청_생성(0, 10, 과거순);
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
    class findRecipesByProduct_성공_테스트 {

        @Test
        void 상품이_포함된_레시피들을_조회할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var recipe1 = 레시피_생성(member, 1L);
            final var recipe2 = 레시피_생성(member, 3L);
            final var recipe3 = 레시피_생성(member, 2L);
            복수_꿀조합_저장(recipe1, recipe2, recipe3);

            final var product_recipe_1_1 = 레시피_안에_들어가는_상품_생성(product1, recipe1);
            final var product_recipe_1_2 = 레시피_안에_들어가는_상품_생성(product1, recipe2);
            final var product_recipe_2_1 = 레시피_안에_들어가는_상품_생성(product2, recipe1);
            final var product_recipe_3_1 = 레시피_안에_들어가는_상품_생성(product3, recipe1);
            final var product_recipe_3_2 = 레시피_안에_들어가는_상품_생성(product3, recipe2);
            복수_꿀조합_상품_저장(product_recipe_1_1, product_recipe_2_1, product_recipe_3_1, product_recipe_1_2,
                    product_recipe_3_2);

            final var recipeImage1_1 = 레시피이미지_생성(recipe1);
            final var recipeImage2_1 = 레시피이미지_생성(recipe2);
            final var recipeImage2_2 = 레시피이미지_생성(recipe2);
            복수_꿀조합_이미지_저장(recipeImage1_1, recipeImage2_1, recipeImage2_2);

            final var page = 페이지요청_생성(0, 10, 좋아요수_내림차순);
            final var expected = List.of(recipe2, recipe1);

            // when
            final var actual = recipeRepository.findRecipesByProduct(product1, page).getContent();

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class findRecipesByFavoriteCountGreaterThanEqual_성공_테스트 {

        @Test
        void 특정_좋아요_수_이상인_모든_꿀조합들을_조회한다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var recipe1 = 레시피_생성(member, 0L);
            final var recipe2 = 레시피_생성(member, 1L);
            final var recipe3 = 레시피_생성(member, 10L);
            final var recipe4 = 레시피_생성(member, 100L);
            복수_꿀조합_저장(recipe1, recipe2, recipe3, recipe4);

            final var expected = List.of(recipe2, recipe3, recipe4);

            // when
            final var actual = recipeRepository.findRecipesByFavoriteCountGreaterThanEqual(1L);

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 특정_좋아요_수_이상인_꿀조합이_없으면_빈_리스트를_반환한다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var recipe1 = 레시피_생성(member, 0L);
            final var recipe2 = 레시피_생성(member, 0L);
            복수_꿀조합_저장(recipe1, recipe2);

            final var expected = Collections.emptyList();

            // when
            final var actual = recipeRepository.findRecipesByFavoriteCountGreaterThanEqual(1L);

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }
}
