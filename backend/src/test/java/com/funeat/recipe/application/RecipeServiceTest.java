package com.funeat.recipe.application;

import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.ImageFixture.여러_이미지_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버3_생성;
import static com.funeat.fixture.PageFixture.과거순;
import static com.funeat.fixture.PageFixture.좋아요수_내림차순;
import static com.funeat.fixture.PageFixture.최신순;
import static com.funeat.fixture.PageFixture.페이지요청_생성;
import static com.funeat.fixture.ProductFixture.레시피_안에_들어가는_상품_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점2점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점4점_생성;
import static com.funeat.fixture.RecipeFixture.레시피_생성;
import static com.funeat.fixture.RecipeFixture.레시피이미지_생성;
import static com.funeat.fixture.RecipeFixture.레시피좋아요요청_생성;
import static com.funeat.fixture.RecipeFixture.레시피추가요청_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.comment.domain.Comment;
import com.funeat.common.ServiceTest;
import com.funeat.common.dto.PageDto;
import com.funeat.member.domain.Member;
import com.funeat.member.dto.MemberRecipeDto;
import com.funeat.member.dto.MemberRecipeProductDto;
import com.funeat.member.dto.MemberRecipesResponse;
import com.funeat.member.exception.MemberException.MemberNotFoundException;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.domain.Product;
import com.funeat.product.exception.ProductException.ProductNotFoundException;
import com.funeat.recipe.dto.RankingRecipeDto;
import com.funeat.recipe.dto.RankingRecipesResponse;
import com.funeat.recipe.dto.RecipeAuthorDto;
import com.funeat.recipe.dto.RecipeCommentCondition;
import com.funeat.recipe.dto.RecipeCommentCreateRequest;
import com.funeat.recipe.dto.RecipeCommentResponse;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeDetailResponse;
import com.funeat.recipe.dto.RecipeDto;
import com.funeat.recipe.exception.RecipeException.RecipeNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class RecipeServiceTest extends ServiceTest {

    @Nested
    class create_성공_테스트 {

        @Test
        void 레시피를_추가할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점4점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var productIds = 상품_아이디_변환(product1, product2, product3);

            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var images = 여러_이미지_생성(3);

            // when
            final var request = 레시피추가요청_생성(productIds);

            final var expected = 레시피_생성(member);

            // when
            final var recipeId = recipeService.create(memberId, images, request);
            final var actual = recipeRepository.findById(recipeId).get();

            // then
            assertThat(actual).usingRecursiveComparison()
                    .ignoringFields("id", "createdAt")
                    .isEqualTo(expected);
            assertThat(actual.getCreatedAt()).isNotNull();
        }
    }

    @Test
    void 레시피의_상세_정보를_조회할_수_있다() {
        // given
        final var category = 카테고리_추가_요청(new Category("간편식사", CategoryType.FOOD, "siksa.jpeg"));
        final var product1 = new Product("불닭볶음면", 1000L, "image.png", "엄청 매운 불닭", category);
        final var product2 = new Product("참치 삼김", 2000L, "image.png", "담백한 참치마요 삼김", category);
        final var product3 = new Product("스트링 치즈", 1500L, "image.png", "고소한 치즈", category);
        복수_상품_저장(product1, product2, product3);
        final var products = List.of(product1, product2, product3);
        final var author = new Member("author", "image.png", "1");
        단일_멤버_저장(author);
        final var authorId = author.getId();

        final var images = 여러_이미지_생성(3);

        final var productIds = List.of(product1.getId(), product2.getId(), product3.getId());
        final var request = new RecipeCreateRequest("제일로 맛있는 레시피", productIds,
                "우선 밥을 넣어요. 그리고 밥을 또 넣어요. 그리고 밥을 또 넣으면.. 끝!!");

        final var recipeId = recipeService.create(authorId, images, request);

        // when
        final var actual = recipeService.getRecipeDetail(authorId, recipeId);

        // then
        final var recipe = recipeRepository.findById(recipeId).get();
        final var expected = RecipeDetailResponse.toResponse(
                recipe, recipeImageRepository.findByRecipe(recipe),
                products, 4500L, false);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Nested
    class create_실패_테스트 {

        @Test
        void 존재하지_않는_멤버가_레시피를_추가하면_예외가_발생한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점4점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var productIds = 상품_아이디_변환(product1, product2, product3);

            final var member = 멤버_멤버1_생성();
            final var wrongMemberId = 단일_멤버_저장(member) + 1L;

            final var images = 여러_이미지_생성(3);

            final var request = 레시피추가요청_생성(productIds);

            // when & then
            assertThatThrownBy(() -> recipeService.create(wrongMemberId, images, request))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        void 존재하지_않는_상품을_레시피에_추가하면_예외가_발생한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점4점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var wrongProductIds = 상품_아이디_변환(product1, product2, product3);
            wrongProductIds.add(4L);

            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var images = 여러_이미지_생성(3);

            final var request = 레시피추가요청_생성(wrongProductIds);

            // when & then
            assertThatThrownBy(() -> recipeService.create(memberId, images, request))
                    .isInstanceOf(ProductNotFoundException.class);
        }
    }

    @Nested
    class findRecipeByMember_성공_테스트 {

        @Test
        void 사용자가_작성한_꿀조합을_조회한다() {
            // given
            final var member1 = 멤버_멤버1_생성();
            복수_멤버_저장(member1);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var recipe1_1 = 레시피_생성(member1);
            final var recipe1_2 = 레시피_생성(member1);
            복수_꿀조합_저장(recipe1_1, recipe1_2);

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

            // when
            final var actual = recipeService.findRecipeByMember(member1.getId(), page);

            // then
            final var expectedRecipes = List.of(recipe1_2, recipe1_1);
            final var expectedRecipesDtos = expectedRecipes.stream()
                    .map(recipe -> {
                        final var findRecipeImages = recipeImageRepository.findByRecipe(recipe);
                        final var productsByRecipe = productRecipeRepository.findProductByRecipe(recipe);
                        final var memberRecipeProductDtos = productsByRecipe.stream()
                                .map(MemberRecipeProductDto::toDto)
                                .collect(Collectors.toList());
                        return MemberRecipeDto.toDto(recipe, findRecipeImages, memberRecipeProductDtos);
                    })
                    .collect(Collectors.toList());
            final var expectedPage = new PageDto(2L, 1L, true, true, 0L, 10L);

            해당멤버의_꿀조합과_페이징_결과를_검증한다(actual, expectedRecipesDtos, expectedPage);
        }

        @Test
        void 사용자가_작성한_꿀조합이_없을때_꿀조합은_빈상태로_조회된다() {
            // given
            final var member1 = 멤버_멤버1_생성();
            단일_멤버_저장(member1);

            final var page = 페이지요청_생성(0, 10, 최신순);

            // when
            final var actual = recipeService.findRecipeByMember(member1.getId(), page);

            // then
            final var expectedRecipes = Collections.emptyList();
            final var expectedPage = new PageDto(0L, 0L, true, true, 0L, 10L);

            해당멤버의_꿀조합과_페이징_결과를_검증한다(actual, expectedRecipes, expectedPage);
        }
    }

    @Nested
    class findRecipeByMember_실패_테스트 {

        @Test
        void 존재하지_않는_멤버가_해당_멤버의_레시피를_조회하면_예외가_발생한다() {
            // given
            final var notExistMemberId = 99999L;
            final var page = 페이지요청_생성(0, 10, 최신순);

            // when & then
            assertThatThrownBy(() -> recipeService.findRecipeByMember(notExistMemberId, page))
                    .isInstanceOf(MemberNotFoundException.class);
        }
    }

    @Nested
    class getSortingRecipes_성공_테스트 {

        @Test
        void 꿀조합을_좋아요가_많은_순으로_정렬할_수_있다() {
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

            final var recipeImage1_1_1 = 레시피이미지_생성(recipe1_1);
            final var recipeImage1_2_1 = 레시피이미지_생성(recipe1_2);
            final var recipeImage1_2_2 = 레시피이미지_생성(recipe1_2);
            복수_꿀조합_이미지_저장(recipeImage1_1_1, recipeImage1_2_1);

            final var page = 페이지요청_생성(0, 10, 좋아요수_내림차순);

            // when
            final var actual = recipeService.getSortingRecipes(page).getRecipes();
            final var expected = List.of(
                    RecipeDto.toDto(recipe1_2, List.of(recipeImage1_2_1, recipeImage1_2_2),
                            List.of(product1, product3)),
                    RecipeDto.toDto(recipe1_3, List.of(), List.of()),
                    RecipeDto.toDto(recipe1_1, List.of(recipeImage1_1_1), List.of(product1, product2, product3)));

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 꿀조합을_최신순으로_정렬할_수_있다() throws InterruptedException {
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

            final var recipeImage1_1_1 = 레시피이미지_생성(recipe1_1);
            final var recipeImage1_2_1 = 레시피이미지_생성(recipe1_2);
            final var recipeImage1_2_2 = 레시피이미지_생성(recipe1_2);
            복수_꿀조합_이미지_저장(recipeImage1_1_1, recipeImage1_2_1);

            final var page = 페이지요청_생성(0, 10, 최신순);

            // when
            final var actual = recipeService.getSortingRecipes(page).getRecipes();
            final var expected = List.of(
                    RecipeDto.toDto(recipe1_3, List.of(), List.of()),
                    RecipeDto.toDto(recipe1_2, List.of(recipeImage1_2_1, recipeImage1_2_2),
                            List.of(product1, product3)),
                    RecipeDto.toDto(recipe1_1, List.of(recipeImage1_1_1), List.of(product1, product2, product3)));

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 꿀조합을_오래된순으로_정렬할_수_있다() {
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

            final var recipeImage1_1_1 = 레시피이미지_생성(recipe1_1);
            final var recipeImage1_2_1 = 레시피이미지_생성(recipe1_2);
            final var recipeImage1_2_2 = 레시피이미지_생성(recipe1_2);
            복수_꿀조합_이미지_저장(recipeImage1_1_1, recipeImage1_2_1);

            final var page = 페이지요청_생성(0, 10, 과거순);

            // when
            final var actual = recipeService.getSortingRecipes(page).getRecipes();
            final var expected = List.of(
                    RecipeDto.toDto(recipe1_1, List.of(recipeImage1_1_1), List.of(product1, product2, product3)),
                    RecipeDto.toDto(recipe1_2, List.of(recipeImage1_2_1, recipeImage1_2_2),
                            List.of(product1, product3)),
                    RecipeDto.toDto(recipe1_3, List.of(), List.of()));

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class likeRecipe_성공_테스트 {

        @Test
        void 꿀조합에_좋아요를_할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점4점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var productIds = 상품_아이디_변환(product1, product2, product3);

            final var author = 멤버_멤버1_생성();
            final var authorId = 단일_멤버_저장(author);
            final var member = 멤버_멤버2_생성();
            final var memberId = 단일_멤버_저장(member);

            final var images = 여러_이미지_생성(3);

            final var createRequest = 레시피추가요청_생성(productIds);
            final var recipeId = recipeService.create(authorId, images, createRequest);

            // when
            final var favoriteRequest = 레시피좋아요요청_생성(true);
            recipeService.likeRecipe(memberId, recipeId, favoriteRequest);

            final var actualRecipe = recipeRepository.findById(recipeId).get();
            final var actualRecipeFavorite = recipeFavoriteRepository.findByMemberAndRecipe(member, actualRecipe).get();

            // then
            assertSoftly(soft -> {
                soft.assertThat(actualRecipe.getFavoriteCount())
                        .isOne();
                soft.assertThat(actualRecipeFavorite.getFavorite())
                        .isTrue();
            });
        }

        @Test
        void 꿀조합에_좋아요를_취소_할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점4점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var productIds = 상품_아이디_변환(product1, product2, product3);

            final var author = 멤버_멤버1_생성();
            final var authorId = 단일_멤버_저장(author);
            final var member = 멤버_멤버2_생성();
            final var memberId = 단일_멤버_저장(member);

            final var images = 여러_이미지_생성(3);

            final var createRequest = 레시피추가요청_생성(productIds);
            final var recipeId = recipeService.create(authorId, images, createRequest);

            final var favoriteRequest = 레시피좋아요요청_생성(true);
            recipeService.likeRecipe(memberId, recipeId, favoriteRequest);

            // when
            final var cancelFavoriteRequest = 레시피좋아요요청_생성(false);
            recipeService.likeRecipe(memberId, recipeId, cancelFavoriteRequest);

            final var actualRecipe = recipeRepository.findById(recipeId).get();
            final var actualRecipeFavorite = recipeFavoriteRepository.findByMemberAndRecipe(member, actualRecipe).get();

            // then
            assertSoftly(soft -> {
                soft.assertThat(actualRecipe.getFavoriteCount())
                        .isZero();
                soft.assertThat(actualRecipeFavorite.getFavorite())
                        .isFalse();
            });
        }
    }

    @Nested
    class likeRecipe_실패_테스트 {

        @Test
        void 존재하지_않는_멤버가_레시피에_좋아요를_하면_예외가_발생한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점4점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var productIds = 상품_아이디_변환(product1, product2, product3);

            final var author = 멤버_멤버1_생성();
            final var authorId = 단일_멤버_저장(author);
            final var wrongMemberId = authorId + 1L;

            final var images = 여러_이미지_생성(3);

            final var createRequest = 레시피추가요청_생성(productIds);
            final var recipeId = recipeService.create(authorId, images, createRequest);

            // when & then
            final var favoriteRequest = 레시피좋아요요청_생성(true);
            assertThatThrownBy(() -> recipeService.likeRecipe(wrongMemberId, recipeId, favoriteRequest))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        void 멤버가_존재하지_않는_레시피에_좋아요를_하면_예외가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var wrongRecipeId = 999L;

            // when & then
            final var favoriteRequest = 레시피좋아요요청_생성(true);
            assertThatThrownBy(() -> recipeService.likeRecipe(memberId, wrongRecipeId, favoriteRequest))
                    .isInstanceOf(RecipeNotFoundException.class);
        }
    }

    @Nested
    class getTop3Recipes_성공_테스트 {

        @Nested
        class 꿀조합_개수에_대한_테스트 {

            @Test
            void 전체_꿀조합이_하나도_없어도_반환값은_있어야한다() {
                // given
                final var expected = RankingRecipesResponse.toResponse(Collections.emptyList());

                // when
                final var actual = recipeService.getTop3Recipes();

                // then
                assertThat(actual).usingRecursiveComparison()
                        .isEqualTo(expected);
            }

            @Test
            void 랭킹_조건에_부합하는_꿀조합이_1개면_꿀조합이_1개_반환된다() {
                // given
                final var member = 멤버_멤버1_생성();
                단일_멤버_저장(member);

                final var now = LocalDateTime.now();
                final var recipe = 레시피_생성(member, 2L, now);
                단일_꿀조합_저장(recipe);

                final var author = RecipeAuthorDto.toDto(member);
                final var rankingRecipeDto = RankingRecipeDto.toDto(recipe, Collections.emptyList(), author);
                final var rankingRecipesDtos = Collections.singletonList(rankingRecipeDto);
                final var expected = RankingRecipesResponse.toResponse(rankingRecipesDtos);

                // when
                final var actual = recipeService.getTop3Recipes();

                // then
                assertThat(actual).usingRecursiveComparison()
                        .isEqualTo(expected);
            }

            @Test
            void 랭킹_조건에_부합하는_꿀조합이_2개면_꿀조합이_2개_반환된다() {
                // given
                final var member = 멤버_멤버1_생성();
                단일_멤버_저장(member);

                final var now = LocalDateTime.now();
                final var recipe1 = 레시피_생성(member, 2L, now.minusDays(1L));
                final var recipe2 = 레시피_생성(member, 2L, now);
                복수_꿀조합_저장(recipe1, recipe2);

                final var author = RecipeAuthorDto.toDto(member);
                final var rankingRecipeDto1 = RankingRecipeDto.toDto(recipe1, Collections.emptyList(), author);
                final var rankingRecipeDto2 = RankingRecipeDto.toDto(recipe2, Collections.emptyList(), author);
                final var rankingRecipesDtos = List.of(rankingRecipeDto2, rankingRecipeDto1);
                final var expected = RankingRecipesResponse.toResponse(rankingRecipesDtos);

                // when
                final var actual = recipeService.getTop3Recipes();

                // then
                assertThat(actual).usingRecursiveComparison()
                        .isEqualTo(expected);
            }

            @Test
            void 전체_꿀조합_중_랭킹이_높은_상위_3개_꿀조합을_구할_수_있다() {
                // given
                final var member = 멤버_멤버1_생성();
                단일_멤버_저장(member);

                final var now = LocalDateTime.now();
                final var recipe1 = 레시피_생성(member, 4L, now.minusDays(10L));
                final var recipe2 = 레시피_생성(member, 6L, now.minusDays(10L));
                final var recipe3 = 레시피_생성(member, 5L, now);
                final var recipe4 = 레시피_생성(member, 6L, now);
                복수_꿀조합_저장(recipe1, recipe2, recipe3, recipe4);

                final var author = RecipeAuthorDto.toDto(member);
                final var rankingRecipeDto1 = RankingRecipeDto.toDto(recipe1, Collections.emptyList(), author);
                final var rankingRecipeDto2 = RankingRecipeDto.toDto(recipe2, Collections.emptyList(), author);
                final var rankingRecipeDto3 = RankingRecipeDto.toDto(recipe3, Collections.emptyList(), author);
                final var rankingRecipeDto4 = RankingRecipeDto.toDto(recipe4, Collections.emptyList(), author);
                final var rankingRecipesDtos = List.of(rankingRecipeDto4, rankingRecipeDto3, rankingRecipeDto2);
                final var expected = RankingRecipesResponse.toResponse(rankingRecipesDtos);

                // when
                final var actual = recipeService.getTop3Recipes();

                // then
                assertThat(actual).usingRecursiveComparison()
                        .isEqualTo(expected);
            }
        }

        @Nested
        class 꿀조합_랭킹_점수에_대한_테스트 {

            @Test
            void 꿀조합_좋아요_수가_같으면_최근_생성된_꿀조합의_랭킹을_더_높게_반환한다() {
                // given
                final var member = 멤버_멤버1_생성();
                단일_멤버_저장(member);

                final var now = LocalDateTime.now();
                final var recipe1 = 레시피_생성(member, 10L, now.minusDays(9L));
                final var recipe2 = 레시피_생성(member, 10L, now.minusDays(4L));
                복수_꿀조합_저장(recipe1, recipe2);

                final var author = RecipeAuthorDto.toDto(member);
                final var rankingRecipeDto1 = RankingRecipeDto.toDto(recipe1, Collections.emptyList(), author);
                final var rankingRecipeDto2 = RankingRecipeDto.toDto(recipe2, Collections.emptyList(), author);
                final var rankingRecipesDtos = List.of(rankingRecipeDto2, rankingRecipeDto1);
                final var expected = RankingRecipesResponse.toResponse(rankingRecipesDtos);

                // when
                final var actual = recipeService.getTop3Recipes();

                // then
                assertThat(actual).usingRecursiveComparison()
                        .isEqualTo(expected);
            }

            @Test
            void 꿀조합_생성_일자가_같으면_좋아요_수가_많은_꿀조합의_랭킹을_더_높게_반환한다() {
                // given
                final var member = 멤버_멤버1_생성();
                단일_멤버_저장(member);

                final var now = LocalDateTime.now();
                final var recipe1 = 레시피_생성(member, 2L, now.minusDays(1L));
                final var recipe2 = 레시피_생성(member, 4L, now.minusDays(1L));
                복수_꿀조합_저장(recipe1, recipe2);

                final var author = RecipeAuthorDto.toDto(member);
                final var rankingRecipeDto1 = RankingRecipeDto.toDto(recipe1, Collections.emptyList(), author);
                final var rankingRecipeDto2 = RankingRecipeDto.toDto(recipe2, Collections.emptyList(), author);
                final var rankingRecipesDtos = List.of(rankingRecipeDto2, rankingRecipeDto1);
                final var expected = RankingRecipesResponse.toResponse(rankingRecipesDtos);

                // when
                final var actual = recipeService.getTop3Recipes();

                // then
                assertThat(actual).usingRecursiveComparison()
                        .isEqualTo(expected);
            }
        }
    }
  
    @Nested
    class writeCommentOfRecipe_성공_테스트 {

        @Test
        void 꿀조합에_댓글을_작성할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점4점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var author = 멤버_멤버1_생성();
            단일_멤버_저장(author);
            final var authorId = author.getId();

            final var images = 여러_이미지_생성(3);

            final var productIds = List.of(product1.getId(), product2.getId(), product3.getId());
            final var recipeCreateRequest = new RecipeCreateRequest("제일로 맛있는 레시피", productIds,
                    "우선 밥을 넣어요. 그리고 밥을 또 넣어요. 그리고 밥을 또 넣으면.. 끝!!");

            final var savedMemberId = 단일_멤버_저장(멤버_멤버1_생성());
            final var savedRecipeId = recipeService.create(authorId, images, recipeCreateRequest);

            // when
            final var request = new RecipeCommentCreateRequest("꿀조합 댓글이에요");
            final var savedCommentId = recipeService.writeCommentOfRecipe(savedMemberId, savedRecipeId, request);

            // then
            final var result = commentRepository.findById(savedCommentId).get();
            final var savedRecipe = recipeRepository.findById(savedRecipeId).get();
            final var savedMember = memberRepository.findById(savedMemberId).get();

            assertThat(result).usingRecursiveComparison()
                    .ignoringFields("id", "createdAt")
                    .isEqualTo(new Comment(savedRecipe, savedMember, request.getComment()));
        }
    }

    @Nested
    class writeCommentOfRecipe_실패_테스트 {

        @Test
        void 존재하지_않은_멤버가_꿀조합에_댓글을_작성하면_예외가_발생한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점4점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var author = new Member("author", "image.png", "1");
            단일_멤버_저장(author);
            final var authorId = author.getId();

            final var images = 여러_이미지_생성(3);

            final var productIds = List.of(product1.getId(), product2.getId(), product3.getId());
            final var recipeCreateRequest = new RecipeCreateRequest("제일로 맛있는 레시피", productIds,
                    "우선 밥을 넣어요. 그리고 밥을 또 넣어요. 그리고 밥을 또 넣으면.. 끝!!");

            final var notExistMemberId = 999999999L;
            final var savedRecipeId = recipeService.create(authorId, images, recipeCreateRequest);
            final var request = new RecipeCommentCreateRequest("꿀조합 댓글이에요");

            // when then
            assertThatThrownBy(() -> recipeService.writeCommentOfRecipe(notExistMemberId, savedRecipeId, request))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        void 존재하지_않은_꿀조합에_댓글을_작성하면_예외가_발생한다() {
            // given
            final var memberId = 단일_멤버_저장(멤버_멤버1_생성());
            final var request = new RecipeCommentCreateRequest("꿀조합 댓글이에요");
            final var notExistRecipeId = 999999999L;

            // when then
            assertThatThrownBy(() -> recipeService.writeCommentOfRecipe(memberId, notExistRecipeId, request))
                    .isInstanceOf(RecipeNotFoundException.class);
        }
    }

    @Nested
    class getCommentsOfRecipe_성공_테스트 {

        @Test
        void 꿀조합에_달린_댓글들을_커서페이징을_통해_조회할_수_있다_총_댓글_15개_중_첫페이지_댓글_10개조회() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점4점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var author = 멤버_멤버1_생성();
            단일_멤버_저장(author);
            final var authorId = author.getId();

            final var images = 여러_이미지_생성(3);

            final var productIds = List.of(product1.getId(), product2.getId(), product3.getId());
            final var recipeCreateRequest = new RecipeCreateRequest("제일로 맛있는 레시피", productIds,
                    "우선 밥을 넣어요. 그리고 밥을 또 넣어요. 그리고 밥을 또 넣으면.. 끝!!");

            final var savedMemberId = 단일_멤버_저장(멤버_멤버1_생성());
            final var savedRecipeId = recipeService.create(authorId, images, recipeCreateRequest);

            for (int i = 1; i <= 15; i++) {
                final var request = new RecipeCommentCreateRequest("꿀조합 댓글이에요" + i);
                recipeService.writeCommentOfRecipe(savedMemberId, savedRecipeId, request);
            }

            // when
            final var result = recipeService.getCommentsOfRecipe(savedRecipeId,
                    new RecipeCommentCondition(null, null));

            //
            final var savedRecipe = recipeRepository.findById(savedRecipeId).get();
            final var savedMember = memberRepository.findById(savedMemberId).get();

            final var expectedCommentResponses = new ArrayList<>();
            for (int i = 0; i < result.getComments().size(); i++) {
                expectedCommentResponses.add(RecipeCommentResponse.toResponse(
                        new Comment(savedRecipe, savedMember, "꿀조합 댓글이에요" + (15 - i))));
            }

            assertThat(result.getHasNext()).isTrue();
            assertThat(result.getTotalElements()).isEqualTo(15);
            assertThat(result.getComments()).hasSize(10);
            assertThat(result.getComments()).usingRecursiveComparison()
                    .ignoringFields("id", "createdAt")
                    .isEqualTo(expectedCommentResponses);
        }

        @Test
        void 꿀조합에_달린_댓글들을_커서페이징을_통해_조회할_수_있다_총_댓글_15개_중_마지막페이지_댓글_5개조회() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점4점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var author = 멤버_멤버1_생성();
            단일_멤버_저장(author);
            final var authorId = author.getId();

            final var images = 여러_이미지_생성(3);

            final var productIds = List.of(product1.getId(), product2.getId(), product3.getId());
            final var recipeCreateRequest = new RecipeCreateRequest("제일로 맛있는 레시피", productIds,
                    "우선 밥을 넣어요. 그리고 밥을 또 넣어요. 그리고 밥을 또 넣으면.. 끝!!");

            final var savedMemberId = 단일_멤버_저장(멤버_멤버1_생성());
            final var savedRecipeId = recipeService.create(authorId, images, recipeCreateRequest);

            for (int i = 1; i <= 15; i++) {
                final var request = new RecipeCommentCreateRequest("꿀조합 댓글이에요" + i);
                recipeService.writeCommentOfRecipe(savedMemberId, savedRecipeId, request);
            }

            // when
            final var result = recipeService.getCommentsOfRecipe(savedRecipeId,
                    new RecipeCommentCondition(6L, 15L));

            //
            final var savedRecipe = recipeRepository.findById(savedRecipeId).get();
            final var savedMember = memberRepository.findById(savedMemberId).get();

            final var expectedCommentResponses = new ArrayList<>();
            for (int i = 0; i < result.getComments().size(); i++) {
                expectedCommentResponses.add(RecipeCommentResponse.toResponse(
                        new Comment(savedRecipe, savedMember, "꿀조합 댓글이에요" + (5 - i))));
            }

            assertThat(result.getHasNext()).isFalse();
            assertThat(result.getTotalElements()).isEqualTo(15);
            assertThat(result.getComments()).hasSize(5);
            assertThat(result.getComments()).usingRecursiveComparison()
                    .ignoringFields("id", "createdAt")
                    .isEqualTo(expectedCommentResponses);
        }
    }

    private <T> void 해당멤버의_꿀조합과_페이징_결과를_검증한다(final MemberRecipesResponse actual, final List<T> expectedRecipesDtos,
                                             final PageDto expectedPage) {
        assertSoftly(soft -> {
            assertThat(actual.getRecipes()).usingRecursiveComparison()
                    .isEqualTo(expectedRecipesDtos);
            assertThat(actual.getPage()).usingRecursiveComparison()
                    .isEqualTo(expectedPage);
        });
    }

    private Category 카테고리_추가_요청(final Category category) {
        return categoryRepository.save(category);
    }

    private List<Long> 상품_아이디_변환(final Product... products) {
        return Stream.of(products)
                .map(Product::getId)
                .collect(Collectors.toList());
    }
}
