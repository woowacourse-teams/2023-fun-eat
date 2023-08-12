package com.funeat.acceptance.recipe;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키를_얻는다;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.승인되지_않음;
import static com.funeat.acceptance.common.CommonSteps.잘못된_요청;
import static com.funeat.acceptance.common.CommonSteps.정상_생성;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.common.CommonSteps.찾을수_없음;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_상세_정보_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_생성_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_추가_요청하고_id_반환;
import static com.funeat.acceptance.recipe.RecipeSteps.여러_사진_요청;
import static com.funeat.auth.exception.AuthErrorCode.LOGIN_MEMBER_NOT_FOUND;
import static com.funeat.exception.CommonErrorCode.REQUEST_VALID_ERROR_CODE;
import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점1점_생성;
import static com.funeat.fixture.RecipeFixture.레시피추가요청_생성;
import static com.funeat.recipe.exception.RecipeErrorCode.RECIPE_NOF_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.product.domain.Product;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeDetailResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@SuppressWarnings("NonAsciiCharacters")
public class RecipeAcceptanceTest extends AcceptanceTest {

    @Nested
    class writeRecipe_성공_테스트 {

        @Test
        void 레시피를_작성한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var productIds = 상품_아이디_변환(product1, product2, product3);
            final var request = 레시피추가요청_생성(productIds);

            final var images = 여러_사진_요청(3);

            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 레시피_생성_요청(request, images, loginCookie);

            // then
            STATUS_CODE를_검증한다(response, 정상_생성);
        }
    }

    @Nested
    class writeRecipe_실패_테스트 {

        @Test
        void 로그인_하지않은_사용자가_레시피_작성시_예외가_발생한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var productIds = 상품_아이디_변환(product1, product2, product3);
            final var request = 레시피추가요청_생성(productIds);

            final var images = 여러_사진_요청(3);

            // when
            final var response = 레시피_생성_요청(request, images, null);

            // then
            final var expectedCode = LOGIN_MEMBER_NOT_FOUND.getCode();
            final var expectedMessage = LOGIN_MEMBER_NOT_FOUND.getMessage();

            STATUS_CODE를_검증한다(response, 승인되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 사용자가_레시피_작성할때_레시피이름_미기입시_예외가_발생한다(final String title) {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var productIds = 상품_아이디_변환(product1, product2, product3);
            final var request = new RecipeCreateRequest(title, productIds, "밥 추가, 밥 추가, 밥 추가.. 끝!!");

            final var images = 여러_사진_요청(3);

            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 레시피_생성_요청(request, images, loginCookie);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "레시피 이름을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @Test
        void 사용자가_레시피_작성할때_상품들이_NULL일시_예외가_발생한다() {
            // given
            final var request = new RecipeCreateRequest("title", null, "밥 추가, 밥 추가, 밥 추가.. 끝!!");

            final var images = 여러_사진_요청(3);

            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 레시피_생성_요청(request, images, loginCookie);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "상품 ID 목록을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @Test
        void 사용자가_레시피_작성할때_상품들이_비어있을시_예외가_발생한다() {
            // given
            final var request = new RecipeCreateRequest("title", Collections.emptyList(), "밥 추가, 밥 추가, 밥 추가.. 끝!!");

            final var images = 여러_사진_요청(3);

            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 레시피_생성_요청(request, images, loginCookie);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "적어도 1개의 상품 ID가 필요합니다. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 사용자가_레시피_작성할때_내용이_비어있을시_예외가_발생한다(final String content) {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var productIds = 상품_아이디_변환(product1, product2, product3);

            final var request = new RecipeCreateRequest("title", productIds, content);

            final var images = 여러_사진_요청(3);

            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 레시피_생성_요청(request, images, loginCookie);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "레시피 내용을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @Test
        void 사용자가_레시피_작성할때_레시피내용이_500자_초과시_예외가_발생한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var productIds = 상품_아이디_변환(product1, product2, product3);

            final var images = 여러_사진_요청(3);

            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var maxContent = "tests".repeat(100) + "a";
            final var request = new RecipeCreateRequest("title", productIds, maxContent);
            final var response = 레시피_생성_요청(request, images, loginCookie);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "리뷰 내용은 최대 500자까지 입력 가능합니다. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }
    }

    @Nested
    class getRecipeDetail_성공_테스트 {

        @Test
        void 레시피의_상세_정보를_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            final var products = List.of(product1, product2, product3);
            복수_상품_저장(product1, product2, product3);
            final var productIds = 상품_아이디_변환(product1, product2, product3);
            final var totalPrice = 상품_총가격_계산(product1, product2, product3);

            final var loginCookie = 로그인_쿠키를_얻는다();

            final var createRequest = 레시피추가요청_생성(productIds);
            final var images = 여러_사진_요청(3);
            final var recipeId = 레시피_추가_요청하고_id_반환(createRequest, images, loginCookie);

            final var recipe = recipeRepository.findById(recipeId).get();
            final var findImages = recipeImageRepository.findByRecipe(recipe);

            final var expected = RecipeDetailResponse.toResponse(recipe, findImages, products, totalPrice, false);

            // when
            final var response = 레시피_상세_정보_요청(loginCookie, recipeId);
            final var actual = response.as(RecipeDetailResponse.class);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            레시피_상세_정보_조회_결과를_검증한다(actual, expected);
        }
    }

    @Nested
    class getRecipeDetail_실패_테스트 {

        @Test
        void 존재하지_않는_레시피_사용자가_레시피_상세_조회시_예외가_발생한다() {
            // given
            final var notExistRecipeId = 99999L;
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 레시피_상세_정보_요청(loginCookie, notExistRecipeId);

            // then
            STATUS_CODE를_검증한다(response, 찾을수_없음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, RECIPE_NOF_FOUND.getCode(), RECIPE_NOF_FOUND.getMessage());
        }
    }

    private void 레시피_상세_정보_조회_결과를_검증한다(final RecipeDetailResponse actual, final RecipeDetailResponse expected) {
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private void RESPONSE_CODE와_MESSAGE를_검증한다(final ExtractableResponse<Response> response, final String expectedCode,
                                              final String expectedMessage) {
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.jsonPath().getString("code"))
                    .isEqualTo(expectedCode);
            softAssertions.assertThat(response.jsonPath().getString("message"))
                    .isEqualTo(expectedMessage);
        });
    }

    private Long 상품_총가격_계산(final Product... products) {
        return Stream.of(products)
                .mapToLong(Product::getPrice)
                .sum();
    }

    private List<Long> 상품_아이디_변환(final Product... products) {
        return Stream.of(products)
                .map(Product::getId)
                .collect(Collectors.toList());
    }
}
