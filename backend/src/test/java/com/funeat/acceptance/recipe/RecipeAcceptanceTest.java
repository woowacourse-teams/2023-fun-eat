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
import static com.funeat.acceptance.common.CommonSteps.페이지를_검증한다;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_검색_결과_조회_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_댓글_작성_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_랭킹_조회_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_목록_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_상세_정보_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_작성_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_좋아요_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.여러명이_레시피_좋아요_요청;
import static com.funeat.auth.exception.AuthErrorCode.LOGIN_MEMBER_NOT_FOUND;
import static com.funeat.exception.CommonErrorCode.REQUEST_VALID_ERROR_CODE;
import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.ImageFixture.이미지1;
import static com.funeat.fixture.ImageFixture.이미지2;
import static com.funeat.fixture.ImageFixture.이미지3;
import static com.funeat.fixture.ImageFixture.이미지4;
import static com.funeat.fixture.MemberFixture.멤버1;
import static com.funeat.fixture.MemberFixture.멤버2;
import static com.funeat.fixture.MemberFixture.멤버3;
import static com.funeat.fixture.PageFixture.FIRST_PAGE;
import static com.funeat.fixture.PageFixture.PAGE_SIZE;
import static com.funeat.fixture.PageFixture.과거순;
import static com.funeat.fixture.PageFixture.마지막페이지O;
import static com.funeat.fixture.PageFixture.응답_페이지_생성;
import static com.funeat.fixture.PageFixture.좋아요수_내림차순;
import static com.funeat.fixture.PageFixture.첫페이지O;
import static com.funeat.fixture.PageFixture.총_데이터_개수;
import static com.funeat.fixture.PageFixture.총_페이지;
import static com.funeat.fixture.PageFixture.최신순;
import static com.funeat.fixture.ProductFixture.상품_망고빙수_가격5000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_애플망고_가격3000원_평점5점_생성;
import static com.funeat.fixture.RecipeFixture.레시피;
import static com.funeat.fixture.RecipeFixture.레시피1;
import static com.funeat.fixture.RecipeFixture.레시피2;
import static com.funeat.fixture.RecipeFixture.레시피3;
import static com.funeat.fixture.RecipeFixture.레시피4;
import static com.funeat.fixture.RecipeFixture.레시피_본문;
import static com.funeat.fixture.RecipeFixture.레시피_제목;
import static com.funeat.fixture.RecipeFixture.레시피좋아요요청_생성;
import static com.funeat.fixture.RecipeFixture.레시피추가요청_생성;
import static com.funeat.fixture.RecipeFixture.존재하지_않는_레시피;
import static com.funeat.fixture.RecipeFixture.좋아요O;
import static com.funeat.fixture.RecipeFixture.좋아요X;
import static com.funeat.recipe.exception.RecipeErrorCode.RECIPE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.member.domain.Member;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.dto.ProductRecipeDto;
import com.funeat.recipe.dto.RankingRecipeDto;
import com.funeat.recipe.dto.RecipeAuthorDto;
import com.funeat.recipe.dto.RecipeCommentCreateRequest;
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
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));

            // when
            final var 응답 = 레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));

            // then
            STATUS_CODE를_검증한다(응답, 정상_생성);
        }

        @Test
        void 이미지가_없어도_레시피를_작성할_수_있다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));

            // when
            final var 응답 = 레시피_작성_요청(로그인_쿠키_획득(멤버1), null, 레시피추가요청_생성(상품));

            // then
            STATUS_CODE를_검증한다(응답, 정상_생성);
        }
    }

    @Nested
    class writeRecipe_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_레시피_작성시_예외가_발생한다(final String cookie) {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));

            // when
            final var 응답 = 레시피_작성_요청(cookie, 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));

            // then
            STATUS_CODE를_검증한다(응답, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 사용자가_레시피_작성할때_레시피이름_미기입시_예외가_발생한다(final String title) {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));

            final var 요청 = 레시피추가요청_생성(title, List.of(상품), 레시피_본문);

            // when
            final var 응답 = 레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 요청);

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    "꿀조합 이름을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @NullSource
        @ParameterizedTest
        void 사용자가_레시피_작성할때_상품들이_NULL일시_예외가_발생한다(final List<Long> productIds) {
            // given
            final var 요청 = 레시피추가요청_생성(레시피_제목, productIds, 레시피_본문);

            // when
            final var 응답 = 레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 요청);

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    "상품 ID 목록을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 사용자가_레시피_작성할때_상품들이_비어있을시_예외가_발생한다() {
            // given
            final var 요청 = new RecipeCreateRequest(레시피_제목, Collections.emptyList(), 레시피_본문);

            // when
            final var 응답 = 레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 요청);

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    "적어도 1개의 상품 ID가 필요합니다. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 사용자가_레시피_작성할때_내용이_비어있을시_예외가_발생한다(final String content) {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));

            final var 요청 = 레시피추가요청_생성(레시피_제목, List.of(상품), content);

            // when
            final var 응답 = 레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 요청);

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    "꿀조합 내용을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 사용자가_레시피_작성할때_레시피내용이_500자_초과시_예외가_발생한다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));

            final var 레시피내용_501자 = ".".repeat(500) + "a";
            final var 요청 = 레시피추가요청_생성("title", List.of(상품), 레시피내용_501자);

            // when
            final var 응답 = 레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 요청);

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    "꿀조합 내용은 최대 500자까지 입력 가능합니다. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }
    }

    @Nested
    class getRecipeDetail_성공_테스트 {

        @Test
        void 레시피의_상세_정보를_조회한다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품1 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));
            final var 상품2 = 단일_상품_저장(상품_삼각김밥_가격3000원_평점1점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품1, 상품2));

            // when
            final var 응답 = 레시피_상세_정보_요청(로그인_쿠키_획득(멤버1), 레시피);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            레시피_상세_정보_조회_결과를_검증한다(응답);
        }
    }

    @Nested
    class getRecipeDetail_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_레시피_상세_조회시_예외가_발생한다(final String cookie) {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));

            // when
            final var 응답 = 레시피_상세_정보_요청(cookie, 레시피);

            // then
            STATUS_CODE를_검증한다(응답, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }

        @Test
        void 존재하지_않는_레시피_사용자가_레시피_상세_조회시_예외가_발생한다() {
            // given & when
            final var 응답 = 레시피_상세_정보_요청(로그인_쿠키_획득(멤버1), 존재하지_않는_레시피);

            // then
            STATUS_CODE를_검증한다(응답, 찾을수_없음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, RECIPE_NOT_FOUND.getCode(), RECIPE_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class likeRecipe_성공_테스트 {

        @Test
        void 레시피에_좋아요를_할_수_있다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));

            // when
            final var 응답 = 레시피_좋아요_요청(로그인_쿠키_획득(멤버1), 레시피, 레시피좋아요요청_생성(좋아요O));

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리_NO_CONTENT);
        }

        @Test
        void 레시피에_좋아요를_취소할_수_있다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));
            레시피_좋아요_요청(로그인_쿠키_획득(멤버1), 레시피, 레시피좋아요요청_생성(좋아요O));

            // when
            final var 응답 = 레시피_좋아요_요청(로그인_쿠키_획득(멤버1), 레시피, 레시피좋아요요청_생성(좋아요X));

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리_NO_CONTENT);
        }
    }

    @Nested
    class likeRecipe_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_레시피에_좋아요를_할때_예외가_발생한다(final String cookie) {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));

            // when
            final var 응답 = 레시피_좋아요_요청(cookie, 레시피, 레시피좋아요요청_생성(좋아요O));

            // then
            STATUS_CODE를_검증한다(응답, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }

        @Test
        void 사용자가_레시피에_좋아요를_할때_좋아요_미기입시_예외가_발생한다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));

            // when
            final var 응답 = 레시피_좋아요_요청(로그인_쿠키_획득(멤버1), 레시피, 레시피좋아요요청_생성(null));

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    "좋아요를 확인해주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 존재하지_않는_레시피에_사용자가_좋아요를_할때_예외가_발생한다() {
            // given & when
            final var 응답 = 레시피_좋아요_요청(로그인_쿠키_획득(멤버1), 존재하지_않는_레시피, 레시피좋아요요청_생성(좋아요O));

            // then
            STATUS_CODE를_검증한다(응답, 찾을수_없음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, RECIPE_NOT_FOUND.getCode(), RECIPE_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class getSearchResults_성공_테스트 {

        @Test
        void 검색어에_해당하는_상품이_포함된_레시피가_2개면_레시피_2개를_반환한다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품1 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));
            final var 상품2 = 단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품1));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지2), 레시피추가요청_생성(상품1, 상품2));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지3), 레시피추가요청_생성(상품2));

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(2L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

            // when
            final var 응답 = 레시피_검색_결과_조회_요청("망고", FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            페이지를_검증한다(응답, 예상_응답_페이지);
            레시피_검색_결과를_검증한다(응답, List.of(레시피2, 레시피3));
        }

        @Test
        void 검색어에_해당하는_상품이_2개고_상품이_포함된_레시피가_1개면_레시피_1개를_반환한다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품1 = 단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(카테고리));
            final var 상품2 = 단일_상품_저장(상품_애플망고_가격3000원_평점5점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품1, 상품2));

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(1L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

            // when
            final var 응답 = 레시피_검색_결과_조회_요청("망고", FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            페이지를_검증한다(응답, 예상_응답_페이지);
            레시피_검색_결과를_검증한다(응답, List.of(레시피));
        }

        @Test
        void 검색_결과에_레시피가_없으면_빈_리스트를_반환한다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(0L), 총_페이지(0L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

            // when
            final var 응답 = 레시피_검색_결과_조회_요청("참치", FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            페이지를_검증한다(응답, 예상_응답_페이지);
            레시피_검색_결과를_검증한다(응답, Collections.emptyList());
        }
    }

    @Nested
    class getSortingRecipes_성공_테스트 {

        @Test
        void 꿀조합을_좋아요가_많은_순으로_정렬할_수_있다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지2), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지3), 레시피추가요청_생성(상품));
            여러명이_레시피_좋아요_요청(List.of(멤버1), 레시피1, 좋아요O);
            여러명이_레시피_좋아요_요청(List.of(멤버1, 멤버2), 레시피2, 좋아요O);

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

            // when
            final var 응답 = 레시피_목록_요청(좋아요수_내림차순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            페이지를_검증한다(응답, 예상_응답_페이지);
            레시피_목록_조회_결과를_검증한다(응답, List.of(레시피2, 레시피1, 레시피3));
        }

        @Test
        void 꿀조합을_최신순으로_정렬할_수_있다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지2), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지3), 레시피추가요청_생성(상품));

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

            // when
            final var 응답 = 레시피_목록_요청(최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            페이지를_검증한다(응답, 예상_응답_페이지);
            레시피_목록_조회_결과를_검증한다(응답, List.of(레시피3, 레시피2, 레시피1));
        }

        @Test
        void 꿀조합을_오래된순으로_정렬할_수_있다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지2), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지3), 레시피추가요청_생성(상품));

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 레시피_목록_요청(과거순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, 예상_응답_페이지);
            레시피_목록_조회_결과를_검증한다(response, List.of(레시피1, 레시피2, 레시피3));
        }
    }

    @Nested
    class getRankingRecipes_성공_테스트 {

        @Test
        void 전체_꿀조합들_중에서_랭킹_TOP3를_조회할_수_있다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지2), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지3), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지4), 레시피추가요청_생성(상품));
            여러명이_레시피_좋아요_요청(List.of(멤버1, 멤버2), 레시피2, 좋아요O);
            여러명이_레시피_좋아요_요청(List.of(멤버1), 레시피3, 좋아요O);
            여러명이_레시피_좋아요_요청(List.of(멤버1, 멤버2, 멤버3), 레시피4, 좋아요O);

            // when
            final var 응답 = 레시피_랭킹_조회_요청();

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            레시피_랭킹_조회_결과를_검증한다(응답, List.of(레시피4, 레시피2, 레시피3));
        }
    }

    @Nested
    class writeRecipeComment_성공_테스트 {

        @Test
        void 꿀조합에_댓글을_작성할_수_있다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));
            final var 꿀조합_작성_응답 = 레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));

            // when
            final var 작성된_꿀조합_아이디 = 작성된_꿀조합_아이디_추출(꿀조합_작성_응답);
            final var 댓글작성자_로그인_쿠키_획득 = 로그인_쿠키_획득(멤버2);
            final var 꿀조합_댓글 = new RecipeCommentCreateRequest("테스트 코멘트 1");

            final var 응답 = 레시피_댓글_작성_요청(댓글작성자_로그인_쿠키_획득, 작성된_꿀조합_아이디, 꿀조합_댓글);

            // then
            STATUS_CODE를_검증한다(응답, 정상_생성);
            꿀조합_댓글_작성_결과를_검증한다(응답, 멤버2, 꿀조합_댓글);
        }
    }

    @Nested
    class writeRecipeComment_실패_테스트 {

        @ParameterizedTest
        @NullAndEmptySource
        void 꿀조합에_댓글을_작성할때_댓글이_비어있을시_예외가_발생한다(final String comment) {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));
            final var 꿀조합_작성_응답 = 레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));

            // when
            final var 작성된_꿀조합_아이디 = 작성된_꿀조합_아이디_추출(꿀조합_작성_응답);
            final var 댓글작성자_로그인_쿠키_획득 = 로그인_쿠키_획득(멤버2);
            final var 꿀조합_댓글 = new RecipeCommentCreateRequest(comment);

            final var 응답 = 레시피_댓글_작성_요청(댓글작성자_로그인_쿠키_획득, 작성된_꿀조합_아이디, 꿀조합_댓글);

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    "꿀조합 댓글을 확인해 주세요. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @Test
        void 꿀조합에_댓글을_작성할때_댓글이_200자_초과시_예외가_발생한다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));
            final var 꿀조합_작성_응답 = 레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));

            // when
            final var 작성된_꿀조합_아이디 = 작성된_꿀조합_아이디_추출(꿀조합_작성_응답);
            final var 댓글작성자_로그인_쿠키_획득 = 로그인_쿠키_획득(멤버2);
            final var 꿀조합_댓글 = new RecipeCommentCreateRequest("1" +
                    "댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다" +
                    "댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다" +
                    "댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다" +
                    "댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다댓글입니다"
            );

            final var 응답 = 레시피_댓글_작성_요청(댓글작성자_로그인_쿠키_획득, 작성된_꿀조합_아이디, 꿀조합_댓글);

            // then
            STATUS_CODE를_검증한다(응답, 잘못된_요청);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, REQUEST_VALID_ERROR_CODE.getCode(),
                    "꿀조합 댓글은 최대 200자까지 입력 가능합니다. " + REQUEST_VALID_ERROR_CODE.getMessage());
        }

        @ParameterizedTest
        @NullAndEmptySource
        void 로그인_하지않은_사용자가_꿀조합_댓글_작성시_예외가_발생한다(final String cookie) {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));
            final var 꿀조합_작성_응답 = 레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));

            // when
            final var 작성된_꿀조합_아이디 = 작성된_꿀조합_아이디_추출(꿀조합_작성_응답);
            final var 꿀조합_댓글 = new RecipeCommentCreateRequest("테스트 코멘트 1");

            final var 응답 = 레시피_댓글_작성_요청(cookie, 작성된_꿀조합_아이디, 꿀조합_댓글);

            // then
            STATUS_CODE를_검증한다(응답, 인증되지_않음);
            RESPONSE_CODE와_MESSAGE를_검증한다(응답, LOGIN_MEMBER_NOT_FOUND.getCode(),
                    LOGIN_MEMBER_NOT_FOUND.getMessage());
        }
    }

    private void 레시피_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Long> recipeIds) {
        final var actual = response.jsonPath().getList("recipes", RecipeDto.class);

        assertThat(actual).extracting(RecipeDto::getId)
                .containsExactlyElementsOf(recipeIds);
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

    private Long 작성된_꿀조합_아이디_추출(final ExtractableResponse<Response> response) {
        return Long.parseLong(response.header("Location").split("/")[3]);
    }

    private void 꿀조합_댓글_작성_결과를_검증한다(final ExtractableResponse<Response> response, final Long memberId,
                                    final RecipeCommentCreateRequest request) {
        final var savedCommentId = Long.parseLong(response.header("Location").split("/")[4]);

        final var findComments = commentRepository.findAll();

        assertSoftly(soft -> {
            soft.assertThat(savedCommentId).isEqualTo(findComments.get(0).getId());
            soft.assertThat(memberId).isEqualTo(findComments.get(0).getMember().getId());
            soft.assertThat(request.getComment()).isEqualTo(findComments.get(0).getComment());
        });
    }
}
