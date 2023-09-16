package com.funeat.acceptance.recipe;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키_획득;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.여러개_사진_명세_요청;
import static com.funeat.acceptance.common.CommonSteps.인증되지_않음;
import static com.funeat.acceptance.common.CommonSteps.잘못된_요청;
import static com.funeat.acceptance.common.CommonSteps.정상_생성;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.common.CommonSteps.정상_처리_NO_CONTENT;
import static com.funeat.acceptance.common.CommonSteps.찾을수_없음;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_검색_결과_조회_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_랭킹_조회_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_목록_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_상세_정보_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_작성_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_좋아요_요청;
import static com.funeat.auth.exception.AuthErrorCode.LOGIN_MEMBER_NOT_FOUND;
import static com.funeat.exception.CommonErrorCode.REQUEST_VALID_ERROR_CODE;
import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.PageFixture.FIRST_PAGE;
import static com.funeat.fixture.PageFixture.PAGE_SIZE;
import static com.funeat.fixture.PageFixture.과거순;
import static com.funeat.fixture.PageFixture.좋아요수_내림차순;
import static com.funeat.fixture.PageFixture.최신순;
import static com.funeat.fixture.ProductFixture.상품_망고빙수_가격5000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_애플망고_가격3000원_평점5점_생성;
import static com.funeat.fixture.RecipeFixture.레시피좋아요요청_생성;
import static com.funeat.fixture.RecipeFixture.레시피추가요청_생성;
import static com.funeat.recipe.exception.RecipeErrorCode.RECIPE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.common.dto.PageDto;
import com.funeat.member.domain.Member;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.dto.ProductRecipeDto;
import com.funeat.recipe.dto.RankingRecipeDto;
import com.funeat.recipe.dto.RecipeAuthorDto;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeDetailResponse;
import com.funeat.recipe.dto.RecipeDto;
import com.funeat.recipe.dto.SearchRecipeResultDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

@SuppressWarnings("NonAsciiCharacters")
public class RecipeAcceptanceTest extends AcceptanceTest {

    @Nested
    class writeRecipe_성공_테스트 {

        @Test
        void 레시피를_작성한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));

            // when
            final var response = 레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L));

            // then
            STATUS_CODE를_검증한다(response, 정상_생성);
        }

        @Test
        void 이미지가_없어도_레시피를_작성할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));

            // when
            final var response = 레시피_작성_요청(로그인_쿠키_획득(1L), null, 레시피추가요청_생성(1L));

            // then
            STATUS_CODE를_검증한다(response, 정상_생성);
        }
    }

    @Nested
    class writeRecipe_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_레시피_작성시_예외가_발생한다(final String cookie) {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));

            // when
            final var response = 레시피_작성_요청(cookie, 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L));

            // then
            STATUS_CODE를_검증한다(response, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 사용자가_레시피_작성할때_레시피이름_미기입시_예외가_발생한다(final String title) {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));

            final var request = new RecipeCreateRequest(title, List.of(1L), "밥 추가, 밥 추가, 밥 추가.. 끝!!");

            // when
            final var response = 레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), request);

            // then
            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, REQUEST_VALID_ERROR_CODE.getCode(),
                    "꿀조합 이름을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @NullSource
        @ParameterizedTest
        void 사용자가_레시피_작성할때_상품들이_NULL일시_예외가_발생한다(final List<Long> productIds) {
            // given
            final var request = new RecipeCreateRequest("title", productIds, "밥 추가, 밥 추가, 밥 추가.. 끝!!");

            // when
            final var response = 레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), request);

            // then
            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, REQUEST_VALID_ERROR_CODE.getCode(),
                    "상품 ID 목록을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 사용자가_레시피_작성할때_상품들이_비어있을시_예외가_발생한다() {
            // given
            final var request = new RecipeCreateRequest("title", Collections.emptyList(), "밥 추가, 밥 추가, 밥 추가.. 끝!!");

            // when
            final var response = 레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), request);

            // then
            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, REQUEST_VALID_ERROR_CODE.getCode(),
                    "적어도 1개의 상품 ID가 필요합니다. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 사용자가_레시피_작성할때_내용이_비어있을시_예외가_발생한다(final String content) {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));

            final var request = new RecipeCreateRequest("title", List.of(1L), content);

            // when
            final var response = 레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), request);

            // then
            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, REQUEST_VALID_ERROR_CODE.getCode(),
                    "꿀조합 내용을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 사용자가_레시피_작성할때_레시피내용이_500자_초과시_예외가_발생한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));

            final var maxContent = "12345".repeat(100) + "a";
            final var request = new RecipeCreateRequest("title", List.of(1L), maxContent);

            // when
            final var response = 레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), request);

            // then
            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, REQUEST_VALID_ERROR_CODE.getCode(),
                    "꿀조합 내용은 최대 500자까지 입력 가능합니다. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }
    }

    @Nested
    class getRecipeDetail_성공_테스트 {

        @Test
        void 레시피의_상세_정보를_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));
            단일_상품_저장(상품_삼각김밥_가격3000원_평점1점_생성(category));

            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L, 2L));

            // when
            final var response = 레시피_상세_정보_요청(로그인_쿠키_획득(1L), 1L);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            레시피_상세_정보_조회_결과를_검증한다(response);
        }
    }

    @Nested
    class getRecipeDetail_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_레시피_상세_조회시_예외가_발생한다(final String cookie) {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));

            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L));

            // when
            final var response = 레시피_상세_정보_요청(cookie, 1L);

            // then
            STATUS_CODE를_검증한다(response, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }

        @Test
        void 존재하지_않는_레시피_사용자가_레시피_상세_조회시_예외가_발생한다() {
            // given && when
            final var response = 레시피_상세_정보_요청(로그인_쿠키_획득(1L), 999L);

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
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));

            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L));

            // when
            final var response = 레시피_좋아요_요청(로그인_쿠키_획득(1L), 1L, 레시피좋아요요청_생성(true));

            // then
            STATUS_CODE를_검증한다(response, 정상_처리_NO_CONTENT);
        }

        @Test
        void 레시피에_좋아요를_취소할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));

            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L));
            레시피_좋아요_요청(로그인_쿠키_획득(1L), 1L, 레시피좋아요요청_생성(true));

            // when
            final var response = 레시피_좋아요_요청(로그인_쿠키_획득(1L), 1L, 레시피좋아요요청_생성(false));

            // then
            STATUS_CODE를_검증한다(response, 정상_처리_NO_CONTENT);
        }
    }

    @Nested
    class likeRecipe_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_레시피에_좋아요를_할때_예외가_발생한다(final String cookie) {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));

            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L));

            // when
            final var response = 레시피_좋아요_요청(cookie, 1L, 레시피좋아요요청_생성(true));

            // then
            STATUS_CODE를_검증한다(response, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }

        @Test
        void 사용자가_레시피에_좋아요를_할때_좋아요_미기입시_예외가_발생한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));

            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L));

            // when
            final var response = 레시피_좋아요_요청(로그인_쿠키_획득(1L), 1L, 레시피좋아요요청_생성(null));

            // then
            STATUS_CODE를_검증한다(response, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, REQUEST_VALID_ERROR_CODE.getCode(),
                    "좋아요를 확인해주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 존재하지_않는_레시피에_사용자가_좋아요를_할때_예외가_발생한다() {
            // given && when
            final var response = 레시피_좋아요_요청(로그인_쿠키_획득(1L), 999L, 레시피좋아요요청_생성(true));

            // then
            STATUS_CODE를_검증한다(response, 찾을수_없음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, RECIPE_NOT_FOUND.getCode(), RECIPE_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class getSearchResults_성공_테스트 {

        @Test
        void 검색어에_해당하는_상품이_포함된_레시피가_2개면_레시피_2개를_반환한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(category));

            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L));
            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("2"), 레시피추가요청_생성(1L, 2L));
            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("3"), 레시피추가요청_생성(2L));

            final var pageDto = new PageDto(2L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 레시피_검색_결과_조회_요청("망고", FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            레시피_검색_결과를_검증한다(response, List.of(2L, 3L));
        }

        @Test
        void 검색어에_해당하는_상품이_2개고_상품이_포함된_레시피가_1개면_레시피_1개를_반환한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(category));
            단일_상품_저장(상품_애플망고_가격3000원_평점5점_생성(category));

            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L, 2L));

            final var pageDto = new PageDto(1L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 레시피_검색_결과_조회_요청("망고", FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            레시피_검색_결과를_검증한다(response, List.of(1L));
        }

        @Test
        void 검색_결과에_레시피가_없으면_빈_리스트를_반환한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));

            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L));

            final var pageDto = new PageDto(0L, 0L, true, true, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 레시피_검색_결과_조회_요청("참치", FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            레시피_검색_결과를_검증한다(response, Collections.emptyList());
        }
    }

    @Nested
    class getSortingRecipes_성공_테스트 {

        @Test
        void 꿀조합을_좋아요가_많은_순으로_정렬할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));

            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L));
            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("2"), 레시피추가요청_생성(1L));
            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("3"), 레시피추가요청_생성(1L));
            레시피_좋아요_요청(로그인_쿠키_획득(1L), 1L, 레시피좋아요요청_생성(true));
            레시피_좋아요_요청(로그인_쿠키_획득(1L), 2L, 레시피좋아요요청_생성(true));
            레시피_좋아요_요청(로그인_쿠키_획득(2L), 2L, 레시피좋아요요청_생성(true));

            final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 레시피_목록_요청(좋아요수_내림차순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            레시피_목록_조회_결과를_검증한다(response, List.of(2L, 1L, 3L));
        }

        @Test
        void 꿀조합을_최신순으로_정렬할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));

            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L));
            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("2"), 레시피추가요청_생성(1L));
            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("3"), 레시피추가요청_생성(1L));

            final var pageDto = new PageDto(3L, 1L, true, true, 0L, 10L);

            // when
            final var response = 레시피_목록_요청(최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            레시피_목록_조회_결과를_검증한다(response, List.of(3L, 2L, 1L));
        }

        @Test
        void 꿀조합을_오래된순으로_정렬할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));

            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L));
            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("2"), 레시피추가요청_생성(1L));
            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("3"), 레시피추가요청_생성(1L));

            final var pageDto = new PageDto(3L, 1L, true, true, 0L, 10L);

            // when
            final var response = 레시피_목록_요청(과거순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            레시피_목록_조회_결과를_검증한다(response, List.of(1L, 2L, 3L));
        }
    }

    @Nested
    class getRankingRecipes_성공_테스트 {

        @Test
        void 전체_꿀조합들_중에서_랭킹_TOP3를_조회할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));

            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("1"), 레시피추가요청_생성(1L));
            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("2"), 레시피추가요청_생성(1L));
            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("3"), 레시피추가요청_생성(1L));
            레시피_작성_요청(로그인_쿠키_획득(1L), 여러개_사진_명세_요청("4"), 레시피추가요청_생성(1L));
            레시피_좋아요_요청(로그인_쿠키_획득(1L), 2L, 레시피좋아요요청_생성(true));
            레시피_좋아요_요청(로그인_쿠키_획득(2L), 2L, 레시피좋아요요청_생성(true));
            레시피_좋아요_요청(로그인_쿠키_획득(1L), 3L, 레시피좋아요요청_생성(true));
            레시피_좋아요_요청(로그인_쿠키_획득(1L), 4L, 레시피좋아요요청_생성(true));
            레시피_좋아요_요청(로그인_쿠키_획득(2L), 4L, 레시피좋아요요청_생성(true));
            레시피_좋아요_요청(로그인_쿠키_획득(3L), 4L, 레시피좋아요요청_생성(true));

            // when
            final var response = 레시피_랭킹_조회_요청();

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            레시피_랭킹_조회_결과를_검증한다(response, List.of(4L, 2L, 3L));
        }
    }

    private void 레시피_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Long> recipeIds) {
        final var actual = response.jsonPath().getList("recipes", RecipeDto.class);

        assertThat(actual).extracting(RecipeDto::getId)
                .containsExactlyElementsOf(recipeIds);
    }

    private void 페이지를_검증한다(final ExtractableResponse<Response> response, final PageDto expected) {
        final var actual = response.jsonPath().getObject("page", PageDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private void 레시피_상세_정보_조회_결과를_검증한다(final ExtractableResponse<Response> response) {
        final var actual = response.as(RecipeDetailResponse.class);
        final var actualAuthor = response.jsonPath().getObject("author", RecipeAuthorDto.class);
        final var actualProducts = response.jsonPath().getList("products", ProductRecipeDto.class);

        assertSoftly(soft -> {
            soft.assertThat(actual.getId()).isEqualTo(1L);
            soft.assertThat(actual.getImages()).hasSize(0);
            soft.assertThat(actual.getTitle()).isEqualTo("The most delicious recipes");
            soft.assertThat(actual.getContent()).isEqualTo("More rice, more rice, more rice.. Done!!");
            soft.assertThat(actual.getTotalPrice()).isEqualTo(4000L);
            soft.assertThat(actual.getFavoriteCount()).isEqualTo(0L);
            soft.assertThat(actual.getFavorite()).isEqualTo(false);
            soft.assertThat(actualAuthor.getNickname()).isEqualTo("member1");
            soft.assertThat(actualAuthor.getProfileImage()).isEqualTo("www.member1.com");
            soft.assertThat(actualProducts).extracting(ProductRecipeDto::getId)
                    .containsExactlyElementsOf(List.of(1L, 2L));
        });
    }

    private void RESPONSE_CODE와_MESSAGE를_검증한다(final ExtractableResponse<Response> response, final String expectedCode,
                                              final String expectedMessage) {
        assertSoftly(soft -> {
            soft.assertThat(response.jsonPath().getString("code"))
                    .isEqualTo(expectedCode);
            soft.assertThat(response.jsonPath().getString("message"))
                    .isEqualTo(expectedMessage);
        });
    }

    private List<RankingRecipeDto> 예상_레시피_랭킹_변환(final List<Recipe> recipes, final Member member) {
        return recipes.stream()
                .map(it -> RankingRecipeDto.toDto(it, Collections.emptyList(), RecipeAuthorDto.toDto(member)))
                .collect(Collectors.toList());
    }

    private void 레시피_랭킹_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Long> recipeIds) {
        final var actual = response.jsonPath()
                .getList("recipes", RankingRecipeDto.class);

        assertThat(actual).extracting(RankingRecipeDto::getId)
                .isEqualTo(recipeIds);
    }

    private void 레시피_검색_결과를_검증한다(final ExtractableResponse<Response> response, final List<Long> recipeIds) {
        final var actual = response.jsonPath()
                .getList("recipes", SearchRecipeResultDto.class);

        assertThat(actual).extracting(SearchRecipeResultDto::getId)
                .containsExactlyElementsOf(recipeIds);
    }
}
