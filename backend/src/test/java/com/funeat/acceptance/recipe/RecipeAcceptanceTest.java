package com.funeat.acceptance.recipe;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키를_얻는다;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.인증되지_않음;
import static com.funeat.acceptance.common.CommonSteps.잘못된_요청;
import static com.funeat.acceptance.common.CommonSteps.정상_생성;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.common.CommonSteps.정상_처리_NO_CONTENT;
import static com.funeat.acceptance.common.CommonSteps.찾을수_없음;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_목록_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_상세_정보_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_생성_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_좋아요_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_추가_요청하고_id_반환;
import static com.funeat.acceptance.recipe.RecipeSteps.여러_사진_요청;
import static com.funeat.auth.exception.AuthErrorCode.LOGIN_MEMBER_NOT_FOUND;
import static com.funeat.exception.CommonErrorCode.REQUEST_VALID_ERROR_CODE;
import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버3_생성;
import static com.funeat.fixture.ProductFixture.레시피_안에_들어가는_상품_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점1점_생성;
import static com.funeat.fixture.RecipeFixture.레시피_생성;
import static com.funeat.fixture.RecipeFixture.레시피이미지_생성;
import static com.funeat.fixture.RecipeFixture.레시피좋아요요청_생성;
import static com.funeat.fixture.RecipeFixture.레시피추가요청_생성;
import static com.funeat.recipe.exception.RecipeErrorCode.RECIPE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.common.dto.PageDto;
import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeDetailResponse;
import com.funeat.recipe.dto.RecipeDto;
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

            STATUS_CODE를_검증한다(response, 인증되지_않음);
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
            final var expectedMessage = "꿀조합 이름을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage();

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
            final var expectedMessage = "꿀조합 내용을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage();

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
            final var expectedMessage = "꿀조합 내용은 최대 500자까지 입력 가능합니다. " + REQUEST_VALID_ERROR_CODE.getMessage();

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
        void 로그인_하지않은_사용자가_레시피_상세_조회시_예외가_발생한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var productIds = 상품_아이디_변환(product1, product2, product3);

            final var loginCookie = 로그인_쿠키를_얻는다();

            final var createRequest = 레시피추가요청_생성(productIds);
            final var images = 여러_사진_요청(3);
            final var recipeId = 레시피_추가_요청하고_id_반환(createRequest, images, loginCookie);

            // when
            final var response = 레시피_상세_정보_요청(null, recipeId);

            // then
            final var expectedCode = LOGIN_MEMBER_NOT_FOUND.getCode();
            final var expectedMessage = LOGIN_MEMBER_NOT_FOUND.getMessage();

            STATUS_CODE를_검증한다(response, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @Test
        void 존재하지_않는_레시피_사용자가_레시피_상세_조회시_예외가_발생한다() {
            // given
            final var notExistRecipeId = 99999L;
            final var loginCookie = 로그인_쿠키를_얻는다();

            // when
            final var response = 레시피_상세_정보_요청(loginCookie, notExistRecipeId);

            // then
            STATUS_CODE를_검증한다(response, 찾을수_없음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, RECIPE_NOT_FOUND.getCode(), RECIPE_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class likeRecipe_성공_테스트 {

        @Test
        void 레시피에_좋아요를_할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var productIds = 상품_아이디_변환(product1, product2, product3);

            final var loginCookie = 로그인_쿠키를_얻는다();

            final var createRequest = 레시피추가요청_생성(productIds);
            final var images = 여러_사진_요청(3);
            final var recipeId = 레시피_추가_요청하고_id_반환(createRequest, images, loginCookie);

            final var favoriteRequest = 레시피좋아요요청_생성(true);

            // when
            final var response = 레시피_좋아요_요청(loginCookie, recipeId, favoriteRequest);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리_NO_CONTENT);
            레시피_좋아요_결과를_검증한다(member, recipeId, 1L, true);
        }

        @Test
        void 레시피에_좋아요를_취소할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var productIds = 상품_아이디_변환(product1, product2, product3);

            final var loginCookie = 로그인_쿠키를_얻는다();

            final var createRequest = 레시피추가요청_생성(productIds);
            final var images = 여러_사진_요청(3);
            final var recipeId = 레시피_추가_요청하고_id_반환(createRequest, images, loginCookie);

            final var favoriteRequest = 레시피좋아요요청_생성(true);
            레시피_좋아요_요청(loginCookie, recipeId, favoriteRequest);

            // when
            final var cancelFavoriteRequest = 레시피좋아요요청_생성(false);
            final var response = 레시피_좋아요_요청(loginCookie, recipeId, cancelFavoriteRequest);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리_NO_CONTENT);
            레시피_좋아요_결과를_검증한다(member, recipeId, 0L, false);
        }
    }

    @Nested
    class likeRecipe_실패_테스트 {

        @Test
        void 로그인_하지않은_사용자가_레시피에_좋아요를_할때_예외가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var productIds = 상품_아이디_변환(product1, product2, product3);

            final var loginCookie = 로그인_쿠키를_얻는다();

            final var createRequest = 레시피추가요청_생성(productIds);
            final var images = 여러_사진_요청(3);
            final var recipeId = 레시피_추가_요청하고_id_반환(createRequest, images, loginCookie);

            final var favoriteRequest = 레시피좋아요요청_생성(true);

            // when
            final var response = 레시피_좋아요_요청(null, recipeId, favoriteRequest);

            // then
            final var expectedCode = LOGIN_MEMBER_NOT_FOUND.getCode();
            final var expectedMessage = LOGIN_MEMBER_NOT_FOUND.getMessage();

            STATUS_CODE를_검증한다(response, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @Test
        void 사용자가_레시피에_좋아요를_할때_좋아요_미기입시_예외가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);
            final var productIds = 상품_아이디_변환(product1, product2, product3);

            final var loginCookie = 로그인_쿠키를_얻는다();

            final var createRequest = 레시피추가요청_생성(productIds);
            final var images = 여러_사진_요청(3);
            final var recipeId = 레시피_추가_요청하고_id_반환(createRequest, images, loginCookie);

            final var favoriteRequest = 레시피좋아요요청_생성(null);

            // when
            final var response = 레시피_좋아요_요청(loginCookie, recipeId, favoriteRequest);

            // then
            final var expectedCode = REQUEST_VALID_ERROR_CODE.getCode();
            final var expectedMessage = "좋아요를 확인해주세요. " + REQUEST_VALID_ERROR_CODE.getMessage();

            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, expectedCode, expectedMessage);
        }

        @Test
        void 존재하지_않는_레시피에_사용자가_좋아요를_할때_예외가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var loginCookie = 로그인_쿠키를_얻는다();

            final var favoriteRequest = 레시피좋아요요청_생성(true);

            // when
            final var wrongRecipeId = 999L;
            final var response = 레시피_좋아요_요청(loginCookie, wrongRecipeId, favoriteRequest);

            // then
            STATUS_CODE를_검증한다(response, 찾을수_없음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, RECIPE_NOT_FOUND.getCode(), RECIPE_NOT_FOUND.getMessage());
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
            복수_꿀조합_이미지_저장(recipeImage1_1_1, recipeImage1_2_1, recipeImage1_2_2);

            final var pageDto = new PageDto(3L, 1L, true, true, 0L, 10L);
            final var sortingRecipes = List.of(
                    RecipeDto.toDto(recipe1_2, List.of(recipeImage1_2_1, recipeImage1_2_2),
                            List.of(product1, product3)),
                    RecipeDto.toDto(recipe1_3, List.of(), List.of()),
                    RecipeDto.toDto(recipe1_1, List.of(recipeImage1_1_1), List.of(product1, product2, product3)));

            // when
            final var response = 레시피_목록_요청("favoriteCount", "desc", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            레시피_목록_조회_결과를_검증한다(response, sortingRecipes, pageDto);
        }

        @Test
        void 꿀조합을_최신순으로_정렬할_수_있다() {
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
            복수_꿀조합_이미지_저장(recipeImage1_1_1, recipeImage1_2_1, recipeImage1_2_2);

            final var pageDto = new PageDto(3L, 1L, true, true, 0L, 10L);
            final var sortingRecipes = List.of(
                    RecipeDto.toDto(recipe1_3, List.of(), List.of()),
                    RecipeDto.toDto(recipe1_2, List.of(recipeImage1_2_1, recipeImage1_2_2),
                            List.of(product1, product3)),
                    RecipeDto.toDto(recipe1_1, List.of(recipeImage1_1_1), List.of(product1, product2, product3)));

            // when
            final var response = 레시피_목록_요청("createdAt", "desc", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            레시피_목록_조회_결과를_검증한다(response, sortingRecipes, pageDto);
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
            복수_꿀조합_이미지_저장(recipeImage1_1_1, recipeImage1_2_1, recipeImage1_2_2);

            final var pageDto = new PageDto(3L, 1L, true, true, 0L, 10L);
            final var sortingRecipes = List.of(
                    RecipeDto.toDto(recipe1_1, List.of(recipeImage1_1_1), List.of(product1, product2, product3)),
                    RecipeDto.toDto(recipe1_2, List.of(recipeImage1_2_1, recipeImage1_2_2),
                            List.of(product1, product3)),
                    RecipeDto.toDto(recipe1_3, List.of(), List.of()));

            // when
            final var response = 레시피_목록_요청("createdAt", "asc", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            레시피_목록_조회_결과를_검증한다(response, sortingRecipes, pageDto);
        }
    }

    private void 레시피_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<RecipeDto> recipes,
                                    final PageDto pageDto) {
        페이지를_검증한다(response, pageDto);
        레시피_목록을_검증한다(response, recipes);
    }

    private void 페이지를_검증한다(final ExtractableResponse<Response> response, final PageDto expected) {
        final var actual = response.jsonPath().getObject("page", PageDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private void 레시피_목록을_검증한다(final ExtractableResponse<Response> response, final List<RecipeDto> expected) {
        final var actual = response.jsonPath().getList("recipes", RecipeDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private void 레시피_좋아요_결과를_검증한다(final Member member, final Long recipeId, final Long expectedFavoriteCount,
                                  final boolean expectedFavorite) {
        final var actualRecipe = recipeRepository.findById(recipeId).get();
        final var actualRecipeFavorite = recipeFavoriteRepository.findByMemberAndRecipe(member, actualRecipe).get();

        assertSoftly(softAssertions -> {
            softAssertions.assertThat(actualRecipe.getFavoriteCount())
                    .isEqualTo(expectedFavoriteCount);
            softAssertions.assertThat(actualRecipeFavorite.getFavorite())
                    .isEqualTo(expectedFavorite);
        });
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
