package com.funeat.acceptance.recipe;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키를_얻는다;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_생성;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_상세_정보_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_추가_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_추가_요청하고_id_반환;
import static com.funeat.acceptance.recipe.RecipeSteps.여러_사진_요청;
import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점_3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점_5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점_2점_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.domain.Product;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeDetailResponse;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class RecipeAcceptanceTest extends AcceptanceTest {

    @Nested
    class writeRecipe_성공_테스트 {

        @Test
        void 레시피를_작성한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점_3점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점_2점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점_5점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var request = new RecipeCreateRequest("제일로 맛있는 레시피",
                    List.of(product1.getId(), product2.getId(), product3.getId()),
                    "우선 밥을 넣어요. 그리고 밥을 또 넣어요. 그리고 밥을 또 넣으면.. 끝!!");
            final var images = 여러_사진_요청(3);
            final var response = 레시피_추가_요청(request, images, loginCookie);

            // then
            STATUS_CODE를_검증한다(response, 정상_생성);
        }
    }


    @Test
    void 레시피의_상세_정보를_조회한다() {
        // given
        final var category = new Category("간편식사", CategoryType.FOOD);
        카테고리_추가_요청(category);

        final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", category);
        final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", category);
        final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", category);
        final var products = List.of(product1, product2, product3);
        복수_상품_추가_요청(products);
        final var loginCookie = 로그인_쿠키를_얻는다();

        final var createRequest = new RecipeCreateRequest("제일로 맛있는 레시피",
                List.of(product1.getId(), product2.getId(), product3.getId()),
                "우선 밥을 넣어요. 그리고 밥을 또 넣어요. 그리고 밥을 또 넣으면.. 끝!!");
        final var images = 여러_사진_요청(3);
        final var recipeId = 레시피_추가_요청하고_id_반환(createRequest, images, loginCookie);
        final var recipe = recipeRepository.findById(recipeId).get();
        final var findImages = recipeImageRepository.findByRecipe(recipe);
        final var expected = RecipeDetailResponse.toResponse(
                recipe, findImages,
                products, 4500L, false);

        // when
        final var response = 레시피_상세_정보_요청(loginCookie, recipeId);
        final var actual = response.as(RecipeDetailResponse.class);

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
        레시피_상세_정보_조회_결과를_검증한다(actual, expected);
    }

    private Long 카테고리_추가_요청(final Category category) {
        return categoryRepository.save(category).getId();
    }

    private void 복수_상품_추가_요청(final List<Product> products) {
        productRepository.saveAll(products);
    }

    private static void 레시피_상세_정보_조회_결과를_검증한다(final RecipeDetailResponse actual, final RecipeDetailResponse expected) {
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
