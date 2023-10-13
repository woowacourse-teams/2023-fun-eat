package com.funeat.acceptance.product;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.product.domain.Category;
import com.funeat.product.dto.ProductInCategoryDto;
import com.funeat.product.dto.ProductResponse;
import com.funeat.product.dto.RankingProductDto;
import com.funeat.product.dto.SearchProductDto;
import com.funeat.product.dto.SearchProductResultDto;
import com.funeat.product.dto.SearchProductsResponse;
import com.funeat.recipe.dto.RecipeDto;
import com.funeat.tag.dto.TagDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키_획득;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.사진_명세_요청;
import static com.funeat.acceptance.common.CommonSteps.여러개_사진_명세_요청;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.common.CommonSteps.찾을수_없음;
import static com.funeat.acceptance.common.CommonSteps.페이지를_검증한다;
import static com.funeat.acceptance.product.ProductSteps.상품_검색_결과_조회_요청;
import static com.funeat.acceptance.product.ProductSteps.상품_랭킹_조회_요청;
import static com.funeat.acceptance.product.ProductSteps.상품_레시피_목록_요청;
import static com.funeat.acceptance.product.ProductSteps.상품_상세_조회_요청;
import static com.funeat.acceptance.product.ProductSteps.상품_자동_완성_검색_요청;
import static com.funeat.acceptance.product.ProductSteps.카테고리별_상품_목록_조회_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_작성_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.여러명이_레시피_좋아요_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_작성_요청;
import static com.funeat.fixture.CategoryFixture.존재하지_않는_카테고리;
import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.ImageFixture.이미지1;
import static com.funeat.fixture.ImageFixture.이미지2;
import static com.funeat.fixture.ImageFixture.이미지3;
import static com.funeat.fixture.ImageFixture.이미지4;
import static com.funeat.fixture.ImageFixture.이미지5;
import static com.funeat.fixture.ImageFixture.이미지6;
import static com.funeat.fixture.ImageFixture.이미지7;
import static com.funeat.fixture.MemberFixture.멤버1;
import static com.funeat.fixture.MemberFixture.멤버2;
import static com.funeat.fixture.MemberFixture.멤버3;
import static com.funeat.fixture.PageFixture.FIRST_PAGE;
import static com.funeat.fixture.PageFixture.PAGE_SIZE;
import static com.funeat.fixture.PageFixture.SECOND_PAGE;
import static com.funeat.fixture.PageFixture.가격_내림차순;
import static com.funeat.fixture.PageFixture.가격_오름차순;
import static com.funeat.fixture.PageFixture.과거순;
import static com.funeat.fixture.PageFixture.리뷰수_내림차순;
import static com.funeat.fixture.PageFixture.마지막페이지O;
import static com.funeat.fixture.PageFixture.마지막페이지X;
import static com.funeat.fixture.PageFixture.응답_페이지_생성;
import static com.funeat.fixture.PageFixture.좋아요수_내림차순;
import static com.funeat.fixture.PageFixture.첫페이지O;
import static com.funeat.fixture.PageFixture.첫페이지X;
import static com.funeat.fixture.PageFixture.총_데이터_개수;
import static com.funeat.fixture.PageFixture.총_페이지;
import static com.funeat.fixture.PageFixture.최신순;
import static com.funeat.fixture.PageFixture.평균_평점_내림차순;
import static com.funeat.fixture.PageFixture.평균_평점_오름차순;
import static com.funeat.fixture.ProductFixture.상품1;
import static com.funeat.fixture.ProductFixture.상품10;
import static com.funeat.fixture.ProductFixture.상품11;
import static com.funeat.fixture.ProductFixture.상품2;
import static com.funeat.fixture.ProductFixture.상품3;
import static com.funeat.fixture.ProductFixture.상품4;
import static com.funeat.fixture.ProductFixture.상품5;
import static com.funeat.fixture.ProductFixture.상품6;
import static com.funeat.fixture.ProductFixture.상품7;
import static com.funeat.fixture.ProductFixture.상품8;
import static com.funeat.fixture.ProductFixture.상품9;
import static com.funeat.fixture.ProductFixture.상품_망고빙수_가격5000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격4000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격5000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_애플망고_가격3000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.존재하지_않는_상품;
import static com.funeat.fixture.RecipeFixture.레시피1;
import static com.funeat.fixture.RecipeFixture.레시피2;
import static com.funeat.fixture.RecipeFixture.레시피3;
import static com.funeat.fixture.RecipeFixture.레시피추가요청_생성;
import static com.funeat.fixture.RecipeFixture.좋아요O;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매X_생성;
import static com.funeat.fixture.ScoreFixture.점수_1점;
import static com.funeat.fixture.ScoreFixture.점수_2점;
import static com.funeat.fixture.ScoreFixture.점수_3점;
import static com.funeat.fixture.ScoreFixture.점수_4점;
import static com.funeat.fixture.ScoreFixture.점수_5점;
import static com.funeat.fixture.TagFixture.태그_단짠단짠_TASTE_생성;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static com.funeat.product.exception.CategoryErrorCode.CATEGORY_NOT_FOUND;
import static com.funeat.product.exception.ProductErrorCode.PRODUCT_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SuppressWarnings("NonAsciiCharacters")
class ProductAcceptanceTest extends AcceptanceTest {

    @Nested
    class getAllProductsInCategory_성공_테스트 {

        @Nested
        class 가격_기준_내림차순으로_카테고리별_상품_목록_조회 {

            @Test
            void 상품_가격이_서로_다르면_가격_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_간편식사_생성();
                단일_카테고리_저장(카테고리);
                final var 상품1 = 단일_상품_저장(상품_삼각김밥_가격3000원_평점5점_생성(카테고리));
                final var 상품2 = 단일_상품_저장(상품_삼각김밥_가격2000원_평점3점_생성(카테고리));
                final var 상품3 = 단일_상품_저장(상품_삼각김밥_가격4000원_평점4점_생성(카테고리));

                final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 카테고리별_상품_목록_조회_요청(1L, 가격_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                카테고리별_상품_목록_조회_결과를_검증한다(응답, List.of(상품3, 상품1, 상품2));
            }

            @Test
            void 상품_가격이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_간편식사_생성();
                final var 카테고리_아이디 = 단일_카테고리_저장(카테고리);
                final var 상품1 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));
                final var 상품2 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
                final var 상품3 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));

                final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 카테고리별_상품_목록_조회_요청(카테고리_아이디, 가격_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                카테고리별_상품_목록_조회_결과를_검증한다(응답, List.of(상품3, 상품2, 상품1));
            }
        }

        @Nested
        class 가격_기준_오름차순으로_카테고리별_상품_목록_조회 {

            @Test
            void 상품_가격이_서로_다르면_가격_기준_오름차순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_간편식사_생성();
                final var 카테고리_아이디 = 단일_카테고리_저장(카테고리);
                final var 상품1 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));
                final var 상품2 = 단일_상품_저장(상품_삼각김밥_가격4000원_평점4점_생성(카테고리));
                final var 상품3 = 단일_상품_저장(상품_삼각김밥_가격2000원_평점3점_생성(카테고리));

                final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 카테고리별_상품_목록_조회_요청(카테고리_아이디, 가격_오름차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                카테고리별_상품_목록_조회_결과를_검증한다(응답, List.of(상품1, 상품3, 상품2));
            }

            @Test
            void 상품_가격이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_간편식사_생성();
                단일_카테고리_저장(카테고리);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));
                단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
                단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));

                final var 예상_응답_페이지 = 응답_페이지_생성(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 카테고리별_상품_목록_조회_요청(1L, 가격_오름차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                카테고리별_상품_목록_조회_결과를_검증한다(응답, List.of(3L, 2L, 1L));
            }
        }

        @Nested
        class 평점_기준_내림차순으로_카테고리별_상품_목록_조회 {

            @Test
            void 상품_평점이_서로_다르면_평점_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_간편식사_생성();
                단일_카테고리_저장(카테고리);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점5점_생성(카테고리));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점1점_생성(카테고리));

                final var 예상_응답_페이지 = 응답_페이지_생성(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 카테고리별_상품_목록_조회_요청(1L, 평균_평점_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                카테고리별_상품_목록_조회_결과를_검증한다(응답, List.of(2L, 1L, 3L));
            }

            @Test
            void 상품_평점이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_간편식사_생성();
                단일_카테고리_저장(카테고리);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점1점_생성(카테고리));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점1점_생성(카테고리));

                final var 예상_응답_페이지 = 응답_페이지_생성(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 카테고리별_상품_목록_조회_요청(1L, 평균_평점_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                카테고리별_상품_목록_조회_결과를_검증한다(응답, List.of(3L, 2L, 1L));
            }
        }

        @Nested
        class 평점_기준_오름차순으로_카테고리별_상품_목록_조회 {

            @Test
            void 상품_평점이_서로_다르면_평점_기준_오름차순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_간편식사_생성();
                단일_카테고리_저장(카테고리);
                단일_상품_저장(상품_삼각김밥_가격2000원_평점1점_생성(카테고리));
                단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점3점_생성(카테고리));

                final var 예상_응답_페이지 = 응답_페이지_생성(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 카테고리별_상품_목록_조회_요청(1L, 평균_평점_오름차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                카테고리별_상품_목록_조회_결과를_검증한다(응답, List.of(1L, 3L, 2L));
            }

            @Test
            void 상품_평점이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_간편식사_생성();
                단일_카테고리_저장(카테고리);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점1점_생성(카테고리));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점1점_생성(카테고리));

                final var 예상_응답_페이지 = 응답_페이지_생성(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 카테고리별_상품_목록_조회_요청(1L, 평균_평점_오름차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                카테고리별_상품_목록_조회_결과를_검증한다(응답, List.of(3L, 2L, 1L));
            }
        }

        @Nested
        class 리뷰수_기준_내림차순으로_카테고리별_상품_목록_조회 {

            @Test
            void 리뷰수가_서로_다르면_리뷰수_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_간편식사_생성();
                final var 카테고리_아이디 = 단일_카테고리_저장(카테고리);
                final var 상품1 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));
                final var 상품2 = 단일_상품_저장(상품_삼각김밥_가격2000원_평점3점_생성(카테고리));
                final var 상품3 = 단일_상품_저장(상품_삼각김밥_가격2000원_평점1점_생성(카테고리));
                final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

                리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품1, 사진_명세_요청(이미지1), 리뷰추가요청_재구매X_생성(점수_3점, List.of(태그)));
                리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품2, 사진_명세_요청(이미지2), 리뷰추가요청_재구매X_생성(점수_3점, List.of(태그)));
                리뷰_작성_요청(로그인_쿠키_획득(멤버2), 상품2, 사진_명세_요청(이미지3), 리뷰추가요청_재구매O_생성(점수_2점, List.of(태그)));

                final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 카테고리별_상품_목록_조회_요청(카테고리_아이디, 리뷰수_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                카테고리별_상품_목록_조회_결과를_검증한다(응답, List.of(상품2, 상품1, 상품3));
            }

            @Test
            void 리뷰수가_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var 카테고리 = 카테고리_간편식사_생성();
                final var 카테고리_아이디 = 단일_카테고리_저장(카테고리);
                final var 상품1 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));
                final var 상품2 = 단일_상품_저장(상품_삼각김밥_가격5000원_평점3점_생성(카테고리));
                final var 상품3 = 단일_상품_저장(상품_삼각김밥_가격3000원_평점1점_생성(카테고리));

                final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

                // when
                final var 응답 = 카테고리별_상품_목록_조회_요청(카테고리_아이디, 리뷰수_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(응답, 정상_처리);
                페이지를_검증한다(응답, 예상_응답_페이지);
                카테고리별_상품_목록_조회_결과를_검증한다(응답, List.of(상품3, 상품2, 상품1));
            }
        }
    }

    @Nested
    class getAllProductsInCategory_실패_테스트 {

        @Test
        void 상품을_정렬할때_카테고리가_존재하지_않으면_예외가_발생한다() {
            // given & when
            final var 응답 = 카테고리별_상품_목록_조회_요청(존재하지_않는_카테고리, 가격_내림차순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 찾을수_없음);
            ERROR_CODE와_MESSAGE를_검증한다(응답, CATEGORY_NOT_FOUND.getCode(), CATEGORY_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class getProductDetail_성공_테스트 {

        @Test
        void 상품_상세_정보를_조회한다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(카테고리));
            final var 태그1 = 단일_태그_저장(태그_맛있어요_TASTE_생성());
            final var 태그2 = 단일_태그_저장(태그_단짠단짠_TASTE_생성());

            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지1), 리뷰추가요청_재구매X_생성(점수_4점, List.of(태그1, 태그2)));
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지2), 리뷰추가요청_재구매X_생성(점수_4점, List.of(태그2)));
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품, 사진_명세_요청(이미지3), 리뷰추가요청_재구매X_생성(점수_1점, List.of(태그2)));

            // when
            final var 응답 = 상품_상세_조회_요청(상품);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            상품_상세_정보_조회_결과를_검증한다(응답);
        }
    }

    @Nested
    class getProductDetail_실패_테스트 {

        @Test
        void 존재하지_않는_상품_상세_정보를_조회할때_예외가_발생한다() {
            // given & when
            final var 응답 = 상품_상세_조회_요청(존재하지_않는_상품);

            // then
            STATUS_CODE를_검증한다(응답, 찾을수_없음);
            ERROR_CODE와_MESSAGE를_검증한다(응답, PRODUCT_NOT_FOUND.getCode(), PRODUCT_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class getRankingProducts_성공_테스트 {

        @Test
        void 전체_상품들_중에서_랭킹_TOP3를_조회할_수_있다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품1 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점4점_생성(카테고리));
            final var 상품2 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));
            final var 상품3 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점4점_생성(카테고리));
            final var 상품4 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(카테고리));
            final var 태그 = 단일_태그_저장(태그_맛있어요_TASTE_생성());

            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품1, 사진_명세_요청(이미지1), 리뷰추가요청_재구매X_생성(점수_3점, List.of(태그)));
            리뷰_작성_요청(로그인_쿠키_획득(멤버2), 상품1, 사진_명세_요청(이미지2), 리뷰추가요청_재구매X_생성(점수_3점, List.of(태그)));
            리뷰_작성_요청(로그인_쿠키_획득(멤버3), 상품1, 사진_명세_요청(이미지3), 리뷰추가요청_재구매X_생성(점수_4점, List.of(태그)));
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품2, 사진_명세_요청(이미지4), 리뷰추가요청_재구매X_생성(점수_5점, List.of(태그)));
            리뷰_작성_요청(로그인_쿠키_획득(멤버2), 상품2, 사진_명세_요청(이미지5), 리뷰추가요청_재구매X_생성(점수_5점, List.of(태그)));
            리뷰_작성_요청(로그인_쿠키_획득(멤버1), 상품3, 사진_명세_요청(이미지6), 리뷰추가요청_재구매X_생성(점수_4점, List.of(태그)));
            리뷰_작성_요청(로그인_쿠키_획득(멤버2), 상품3, 사진_명세_요청(이미지7), 리뷰추가요청_재구매X_생성(점수_5점, List.of(태그)));

            // when
            final var 응답 = 상품_랭킹_조회_요청();

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            상품_랭킹_조회_결과를_검증한다(응답, List.of(상품2, 상품3, 상품1));
        }
    }

    @Nested
    class searchProducts_성공_테스트 {

        @Test
        void 검색어가_포함된_상품들을_검색한다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품1 = 단일_상품_저장(상품_애플망고_가격3000원_평점5점_생성(카테고리));
            final var 상품2 = 단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(카테고리));

            // when
            final var 응답 = 상품_자동_완성_검색_요청("망고", 0L);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            상품_자동_완성_검색_결과를_검증한다(응답, List.of(상품2, 상품1));
        }

        @Test
        void 검색어가_포함된_상품이_없으면_빈_리스트를_반환한다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            반복_애플망고_상품_저장(2, 카테고리);

            // when
            final var 응답 = 상품_자동_완성_검색_요청("김밥", 0L);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            상품_자동_완성_검색_결과를_검증한다(응답, Collections.emptyList());
        }

        @Test
        void 페이지가_넘어가도_중복없이_결과를_조회한다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(카테고리));
            반복_애플망고_상품_저장(15, 카테고리);

            // when
            final var 응답1 = 상품_자동_완성_검색_요청("망고", 0L);

            final var result = 응답1.as(SearchProductsResponse.class).getProducts();
            final var lastId = result.get(result.size() - 1).getId();
            final var 응답2 = 상품_자동_완성_검색_요청("망고", lastId);

            // then
            STATUS_CODE를_검증한다(응답1, 정상_처리);
            STATUS_CODE를_검증한다(응답2, 정상_처리);

            결과값이_이전_요청_결과값에_중복되는지_검증(응답1, 응답2);
        }

        @Test
        void 페이지가_넘어가도_시작되는_단어_우선_조회한다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(카테고리));
            반복_애플망고_상품_저장(9, 카테고리);
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(카테고리));

            // when
            final var 응답 = 상품_자동_완성_검색_요청("망고", 0L);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            상품_자동_완성_검색_결과를_검증한다(응답, List.of(상품11, 상품1, 상품10, 상품9, 상품8, 상품7, 상품6, 상품5, 상품4, 상품3));
        }
    }

    @Nested
    class getSearchResults_성공_테스트 {

        @Test
        void 상품_검색_결과들을_조회한다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            단일_상품_저장(상품_애플망고_가격3000원_평점5점_생성(카테고리));
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(카테고리));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(2L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 상품_검색_결과_조회_요청("망고", FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, 예상_응답_페이지);
            상품_검색_결과를_검증한다(response, List.of(상품2, 상품1));
        }

        @Test
        void 검색_결과에_상품이_없으면_빈_리스트를_반환한다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            단일_상품_저장(상품_애플망고_가격3000원_평점5점_생성(카테고리));
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(카테고리));

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(0L), 총_페이지(0L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

            // when
            final var 응답 = 상품_검색_결과_조회_요청("김밥", FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            페이지를_검증한다(응답, 예상_응답_페이지);
            상품_검색_결과를_검증한다(응답, Collections.emptyList());
        }

        @Test
        void 페이지가_넘어가도_중복없이_상품_결과를_조회한다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(카테고리));
            반복_애플망고_상품_저장(10, 카테고리);

            final var 예상_응답_페이지1 = 응답_페이지_생성(총_데이터_개수(11L), 총_페이지(2L), 첫페이지O, 마지막페이지X, FIRST_PAGE, PAGE_SIZE);
            final var 예상_응답_페이지2 = 응답_페이지_생성(총_데이터_개수(11L), 총_페이지(2L), 첫페이지X, 마지막페이지O, SECOND_PAGE, PAGE_SIZE);

            // when
            final var 응답1 = 상품_검색_결과_조회_요청("망고", FIRST_PAGE);
            final var 응답2 = 상품_검색_결과_조회_요청("망고", SECOND_PAGE);

            // then
            STATUS_CODE를_검증한다(응답1, 정상_처리);
            페이지를_검증한다(응답1, 예상_응답_페이지1);

            STATUS_CODE를_검증한다(응답2, 정상_처리);
            페이지를_검증한다(응답2, 예상_응답_페이지2);

            결과값이_이전_요청_결과값에_중복되는지_검증(응답1, 응답2);
        }

        @Test
        void 페이지가_넘어가도_시작되는_단어_우선_조회한다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(카테고리));
            반복_애플망고_상품_저장(9, 카테고리);
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(카테고리));

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(11L), 총_페이지(2L), 첫페이지O, 마지막페이지X, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 상품_검색_결과_조회_요청("망고", FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, 예상_응답_페이지);
            상품_검색_결과를_검증한다(response, List.of(상품11, 상품1, 상품10, 상품9, 상품8, 상품7, 상품6, 상품5, 상품4, 상품3));
        }
    }

    @Nested
    class getProductRecipes_성공_테스트 {

        @Test
        void 해당_상품의_꿀조합_목록을_좋아요가_많은_순으로_조회할_수_있다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지2), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지3), 레시피추가요청_생성(상품));
            여러명이_레시피_좋아요_요청(List.of(멤버1), 레시피1, 좋아요O);
            여러명이_레시피_좋아요_요청(List.of(멤버2, 멤버3), 레시피2, 좋아요O);

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 상품_레시피_목록_요청(상품, 좋아요수_내림차순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, 예상_응답_페이지);
            상품_레시피_목록_조회_결과를_검증한다(response, List.of(레시피2, 레시피1, 레시피3));
        }

        @Test
        void 해당_상품의_꿀조합_목록을_최신순으로_조회할_수_있다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지2), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지3), 레시피추가요청_생성(상품));

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 상품_레시피_목록_요청(상품, 최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, 예상_응답_페이지);
            상품_레시피_목록_조회_결과를_검증한다(response, List.of(레시피3, 레시피2, 레시피1));
        }

        @Test
        void 해당_상품의_꿀조합_목록을_오래된순으로_조회할_수_있다() {
            // given
            final var 카테고리 = 카테고리_간편식사_생성();
            단일_카테고리_저장(카테고리);
            final var 상품 = 단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(카테고리));

            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지1), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지2), 레시피추가요청_생성(상품));
            레시피_작성_요청(로그인_쿠키_획득(멤버1), 여러개_사진_명세_요청(이미지3), 레시피추가요청_생성(상품));

            final var 예상_응답_페이지 = 응답_페이지_생성(총_데이터_개수(3L), 총_페이지(1L), 첫페이지O, 마지막페이지O, FIRST_PAGE, PAGE_SIZE);

            // when
            final var 응답 = 상품_레시피_목록_요청(상품, 과거순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(응답, 정상_처리);
            페이지를_검증한다(응답, 예상_응답_페이지);
            상품_레시피_목록_조회_결과를_검증한다(응답, List.of(레시피1, 레시피2, 레시피3));
        }
    }

    private void 카테고리별_상품_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Long> productIds) {
        final var actual = response.jsonPath()
                .getList("products", ProductInCategoryDto.class);

        assertThat(actual).extracting(ProductInCategoryDto::getId)
                .containsExactlyElementsOf(productIds);
    }

    private void ERROR_CODE와_MESSAGE를_검증한다(final ExtractableResponse<Response> response, final String expectedCode,
                                           final String expectedMessage) {
        assertSoftly(soft -> {
            soft.assertThat(response.jsonPath().getString("code"))
                    .isEqualTo(expectedCode);
            soft.assertThat(response.jsonPath().getString("message"))
                    .isEqualTo(expectedMessage);
        });
    }

    private void 상품_상세_정보_조회_결과를_검증한다(final ExtractableResponse<Response> response) {
        final var actual = response.as(ProductResponse.class);
        final var actualTags = response.jsonPath()
                .getList("tags", TagDto.class);

        assertSoftly(soft -> {
            soft.assertThat(actual.getId()).isEqualTo(1L);
            soft.assertThat(actual.getName()).isEqualTo("삼각김밥");
            soft.assertThat(actual.getPrice()).isEqualTo(1000L);
            soft.assertThat(actual.getImage()).isEqualTo("image.png");
            soft.assertThat(actual.getContent()).isEqualTo("맛있는 삼각김밥");
            soft.assertThat(actual.getAverageRating()).isEqualTo(3.0);
            soft.assertThat(actual.getReviewCount()).isEqualTo(3L);
            soft.assertThat(actualTags).extracting("id").containsExactly(2L, 1L);
        });
    }

    private void 상품_랭킹_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Long> productIds) {
        final var actual = response.jsonPath()
                .getList("products", RankingProductDto.class);

        assertThat(actual).extracting(RankingProductDto::getId)
                .isEqualTo(productIds);
    }

    private void 상품_자동_완성_검색_결과를_검증한다(final ExtractableResponse<Response> response, final List<Long> productIds) {
        final var actual = response.jsonPath()
                .getList("products", SearchProductDto.class);

        assertThat(actual).extracting(SearchProductDto::getId)
                .isEqualTo(productIds);
    }

    private void 결과값이_이전_요청_결과값에_중복되는지_검증(final ExtractableResponse<Response> response1,
                                          final ExtractableResponse<Response> response2) {
        final var lastResponses = response1.jsonPath()
                .getList("products", SearchProductResultDto.class);
        final var currentResponse = response2.jsonPath()
                .getList("products", SearchProductResultDto.class).get(0);

        assertThat(lastResponses).usingRecursiveFieldByFieldElementComparator()
                .doesNotContain(currentResponse);
    }

    private void 상품_검색_결과를_검증한다(final ExtractableResponse<Response> response, final List<Long> productIds) {
        final var actual = response.jsonPath()
                .getList("products", SearchProductResultDto.class);

        assertThat(actual).extracting(SearchProductResultDto::getId)
                .containsExactlyElementsOf(productIds);
    }

    private void 상품_레시피_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Long> recipeIds) {
        final var actual = response.jsonPath().getList("recipes", RecipeDto.class);

        assertThat(actual).extracting(RecipeDto::getId)
                .containsExactlyElementsOf(recipeIds);
    }

    private void 반복_애플망고_상품_저장(final int repeat, final Category category) {
        for (int i = 0; i < repeat; i++) {
            단일_상품_저장(상품_애플망고_가격3000원_평점5점_생성(category));
        }
    }
}
