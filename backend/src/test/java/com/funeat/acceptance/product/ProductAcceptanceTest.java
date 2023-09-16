package com.funeat.acceptance.product;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키_획득;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.사진_명세_요청;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.common.CommonSteps.찾을수_없음;
import static com.funeat.acceptance.product.ProductSteps.상품_검색_결과_조회_요청;
import static com.funeat.acceptance.product.ProductSteps.상품_랭킹_조회_요청;
import static com.funeat.acceptance.product.ProductSteps.상품_레시피_목록_요청;
import static com.funeat.acceptance.product.ProductSteps.상품_상세_조회_요청;
import static com.funeat.acceptance.product.ProductSteps.상품_자동_완성_검색_요청;
import static com.funeat.acceptance.product.ProductSteps.카테고리별_상품_목록_조회_요청;
import static com.funeat.acceptance.recipe.RecipeSteps.레시피_좋아요_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_작성_요청;
import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.PageFixture.FIRST_PAGE;
import static com.funeat.fixture.PageFixture.PAGE_SIZE;
import static com.funeat.fixture.PageFixture.SECOND_PAGE;
import static com.funeat.fixture.PageFixture.가격_내림차순;
import static com.funeat.fixture.PageFixture.가격_오름차순;
import static com.funeat.fixture.PageFixture.과거순;
import static com.funeat.fixture.PageFixture.리뷰수_내림차순;
import static com.funeat.fixture.PageFixture.좋아요수_내림차순;
import static com.funeat.fixture.PageFixture.최신순;
import static com.funeat.fixture.PageFixture.평균_평점_내림차순;
import static com.funeat.fixture.PageFixture.평균_평점_오름차순;
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
import static com.funeat.fixture.RecipeFixture.레시피좋아요요청_생성;
import static com.funeat.fixture.RecipeFixture.레시피추가요청_생성;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매X_생성;
import static com.funeat.fixture.TagFixture.태그_간식_ETC_생성;
import static com.funeat.fixture.TagFixture.태그_단짠단짠_TASTE_생성;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static com.funeat.product.exception.CategoryErrorCode.CATEGORY_NOT_FOUND;
import static com.funeat.product.exception.ProductErrorCode.PRODUCT_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.acceptance.recipe.RecipeSteps;
import com.funeat.common.dto.PageDto;
import com.funeat.product.domain.Category;
import com.funeat.product.dto.ProductInCategoryDto;
import com.funeat.product.dto.ProductResponse;
import com.funeat.product.dto.RankingProductDto;
import com.funeat.product.dto.SearchProductDto;
import com.funeat.product.dto.SearchProductResultDto;
import com.funeat.recipe.dto.RecipeDto;
import com.funeat.tag.dto.TagDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ProductAcceptanceTest extends AcceptanceTest {

    @Nested
    class getAllProductsInCategory_성공_테스트 {

        @Nested
        class 가격_기준_내림차순으로_카테고리별_상품_목록_조회 {

            @Test
            void 상품_가격이_서로_다르면_가격_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격3000원_평점5점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점3점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격4000원_평점4점_생성(category));

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(1L, 가격_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, List.of(3L, 1L, 2L));
            }

            @Test
            void 상품_가격이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(1L, 가격_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, List.of(3L, 2L, 1L));
            }
        }

        @Nested
        class 가격_기준_오름차순으로_카테고리별_상품_목록_조회 {

            @Test
            void 상품_가격이_서로_다르면_가격_기준_오름차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격4000원_평점4점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점3점_생성(category));

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(1L, 가격_오름차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, List.of(1L, 3L, 2L));
            }

            @Test
            void 상품_가격이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(1L, 가격_오름차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, List.of(3L, 2L, 1L));
            }
        }

        @Nested
        class 평점_기준_내림차순으로_카테고리별_상품_목록_조회 {

            @Test
            void 상품_평점이_서로_다르면_평점_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점5점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점1점_생성(category));

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(1L, 평균_평점_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, List.of(2L, 1L, 3L));
            }

            @Test
            void 상품_평점이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점1점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점1점_생성(category));

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(1L, 평균_평점_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, List.of(3L, 2L, 1L));
            }
        }

        @Nested
        class 평점_기준_오름차순으로_카테고리별_상품_목록_조회 {

            @Test
            void 상품_평점이_서로_다르면_평점_기준_오름차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격2000원_평점1점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점3점_생성(category));

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(1L, 평균_평점_오름차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, List.of(1L, 3L, 2L));
            }

            @Test
            void 상품_평점이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점1점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점1점_생성(category));

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(1L, 평균_평점_오름차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, List.of(3L, 2L, 1L));
            }
        }

        @Nested
        class 리뷰수_기준_내림차순으로_카테고리별_상품_목록_조회 {

            @Test
            void 리뷰수가_서로_다르면_리뷰수_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점3점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격2000원_평점1점_생성(category));
                단일_태그_저장(태그_맛있어요_TASTE_생성());

                리뷰_작성_요청(로그인_쿠키_획득(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매X_생성(3L, List.of(1L)));
                리뷰_작성_요청(로그인_쿠키_획득(1L), 2L, 사진_명세_요청("3"), 리뷰추가요청_재구매X_생성(3L, List.of(1L)));
                리뷰_작성_요청(로그인_쿠키_획득(2L), 2L, 사진_명세_요청("2"), 리뷰추가요청_재구매O_생성(2L, List.of(1L)));

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(1L, 리뷰수_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, List.of(2L, 1L, 3L));
            }

            @Test
            void 리뷰수가_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                단일_카테고리_저장(category);
                단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격5000원_평점3점_생성(category));
                단일_상품_저장(상품_삼각김밥_가격3000원_평점1점_생성(category));

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(1L, 리뷰수_내림차순, FIRST_PAGE);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, List.of(3L, 2L, 1L));
            }
        }
    }

    @Nested
    class getAllProductsInCategory_실패_테스트 {

        @Test
        void 상품을_정렬할때_카테고리가_존재하지_않으면_예외가_발생한다() {
            // given && when
            final var response = 카테고리별_상품_목록_조회_요청(9999L, 가격_내림차순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 찾을수_없음);
            ERROR_CODE와_MESSAGE를_검증한다(response, CATEGORY_NOT_FOUND.getCode(), CATEGORY_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class getProductDetail_성공_테스트 {

        @Test
        void 상품_상세_정보를_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점3점_생성(category));
            복수_태그_저장(태그_맛있어요_TASTE_생성(), 태그_단짠단짠_TASTE_생성(), 태그_간식_ETC_생성());

            리뷰_작성_요청(로그인_쿠키_획득(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매X_생성(4L, List.of(1L, 2L, 3L)));
            리뷰_작성_요청(로그인_쿠키_획득(1L), 1L, 사진_명세_요청("2"), 리뷰추가요청_재구매X_생성(4L, List.of(2L, 3L)));
            리뷰_작성_요청(로그인_쿠키_획득(1L), 1L, 사진_명세_요청("3"), 리뷰추가요청_재구매X_생성(1L, List.of(2L)));

            // when
            final var response = 상품_상세_조회_요청(1L);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            상품_상세_정보_조회_결과를_검증한다(response);
        }
    }

    @Nested
    class getProductDetail_실패_테스트 {

        @Test
        void 존재하지_않는_상품_상세_정보를_조회할때_예외가_발생한다() {
            // given
            final var notExistProductId = 99999L;

            // when
            final var response = 상품_상세_조회_요청(notExistProductId);

            // then
            STATUS_CODE를_검증한다(response, 찾을수_없음);
            ERROR_CODE와_MESSAGE를_검증한다(response, PRODUCT_NOT_FOUND.getCode(), PRODUCT_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class getRankingProducts_성공_테스트 {

        @Test
        void 전체_상품들_중에서_랭킹_TOP3를_조회할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점4점_생성(category));
            단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));
            단일_상품_저장(상품_삼각김밥_가격1000원_평점4점_생성(category));
            단일_상품_저장(상품_삼각김밥_가격1000원_평점1점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            리뷰_작성_요청(로그인_쿠키_획득(1L), 1L, 사진_명세_요청("1"), 리뷰추가요청_재구매X_생성(3L, List.of(1L)));
            리뷰_작성_요청(로그인_쿠키_획득(2L), 1L, 사진_명세_요청("2"), 리뷰추가요청_재구매X_생성(3L, List.of(1L)));
            리뷰_작성_요청(로그인_쿠키_획득(3L), 1L, 사진_명세_요청("3"), 리뷰추가요청_재구매X_생성(4L, List.of(1L)));
            리뷰_작성_요청(로그인_쿠키_획득(1L), 2L, 사진_명세_요청("4"), 리뷰추가요청_재구매X_생성(5L, List.of(1L)));
            리뷰_작성_요청(로그인_쿠키_획득(2L), 2L, 사진_명세_요청("5"), 리뷰추가요청_재구매X_생성(5L, List.of(1L)));
            리뷰_작성_요청(로그인_쿠키_획득(1L), 3L, 사진_명세_요청("6"), 리뷰추가요청_재구매X_생성(4L, List.of(1L)));
            리뷰_작성_요청(로그인_쿠키_획득(2L), 3L, 사진_명세_요청("7"), 리뷰추가요청_재구매X_생성(5L, List.of(1L)));

            // when
            final var response = 상품_랭킹_조회_요청();

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            상품_랭킹_조회_결과를_검증한다(response, List.of(2L, 3L, 1L));
        }
    }

    @Nested
    class searchProducts_성공_테스트 {

        @Test
        void 검색어가_포함된_상품들을_검색한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_애플망고_가격3000원_평점5점_생성(category));
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(category));

            final var pageDto = new PageDto(2L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 상품_자동_완성_검색_요청("망고", FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            상품_자동_완성_검색_결과를_검증한다(response, List.of(2L, 1L));
        }

        @Test
        void 검색어가_포함된_상품이_없으면_빈_리스트를_반환한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_애플망고_가격3000원_평점5점_생성(category));
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(category));

            final var pageDto = new PageDto(0L, 0L, true, true, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 상품_자동_완성_검색_요청("김밥", FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            상품_자동_완성_검색_결과를_검증한다(response, Collections.emptyList());
        }

        @Test
        void 페이지가_넘어가도_중복없이_결과를_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(category));
            반복_애플망고_상품_저장(10, category);

            final var pageDto1 = new PageDto(11L, 2L, true, false, FIRST_PAGE, PAGE_SIZE);
            final var pageDto2 = new PageDto(11L, 2L, false, true, SECOND_PAGE, PAGE_SIZE);

            // when
            final var response1 = 상품_자동_완성_검색_요청("망고", FIRST_PAGE);
            final var response2 = 상품_자동_완성_검색_요청("망고", SECOND_PAGE);

            // then
            STATUS_CODE를_검증한다(response1, 정상_처리);
            페이지를_검증한다(response1, pageDto1);

            STATUS_CODE를_검증한다(response2, 정상_처리);
            페이지를_검증한다(response2, pageDto2);

            결과값이_이전_요청_결과값에_중복되는지_검증(response1, response2);
        }

        @Test
        void 페이지가_넘어가도_시작되는_단어_우선_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(category));
            반복_애플망고_상품_저장(9, category);
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(category));

            final var pageDto = new PageDto(11L, 2L, true, false, 0L, PAGE_SIZE);

            // when
            final var response = 상품_자동_완성_검색_요청("망고", FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            상품_자동_완성_검색_결과를_검증한다(response, List.of(11L, 1L, 10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L));
        }
    }

    @Nested
    class getSearchResults_성공_테스트 {

        @Test
        void 상품_검색_결과들을_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_애플망고_가격3000원_평점5점_생성(category));
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(category));
            단일_태그_저장(태그_맛있어요_TASTE_생성());

            final var pageDto = new PageDto(2L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 상품_검색_결과_조회_요청("망고", FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            상품_검색_결과를_검증한다(response, List.of(2L, 1L));
        }

        @Test
        void 검색_결과에_상품이_없으면_빈_리스트를_반환한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_애플망고_가격3000원_평점5점_생성(category));
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(category));

            final var pageDto = new PageDto(0L, 0L, true, true, FIRST_PAGE, PAGE_SIZE);

            // when
            final var response = 상품_검색_결과_조회_요청("김밥", FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            상품_검색_결과를_검증한다(response, Collections.emptyList());
        }

        @Test
        void 페이지가_넘어가도_중복없이_상품_결과를_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(category));
            반복_애플망고_상품_저장(10, category);

            final var pageDto1 = new PageDto(11L, 2L, true, false, FIRST_PAGE, PAGE_SIZE);
            final var pageDto2 = new PageDto(11L, 2L, false, true, SECOND_PAGE, PAGE_SIZE);

            // when
            final var response1 = 상품_검색_결과_조회_요청("망고", FIRST_PAGE);
            final var response2 = 상품_검색_결과_조회_요청("망고", SECOND_PAGE);

            // then
            STATUS_CODE를_검증한다(response1, 정상_처리);
            페이지를_검증한다(response1, pageDto1);

            STATUS_CODE를_검증한다(response2, 정상_처리);
            페이지를_검증한다(response2, pageDto2);

            결과값이_이전_요청_결과값에_중복되는지_검증(response1, response2);
        }

        @Test
        void 페이지가_넘어가도_시작되는_단어_우선_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(category));
            반복_애플망고_상품_저장(9, category);
            단일_상품_저장(상품_망고빙수_가격5000원_평점4점_생성(category));

            final var pageDto = new PageDto(11L, 2L, true, false, 0L, PAGE_SIZE);

            // when
            final var response = 상품_검색_결과_조회_요청("망고", FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            상품_검색_결과를_검증한다(response, List.of(11L, 1L, 10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L));
        }
    }

    @Nested
    class getProductRecipes_성공_테스트 {

        @Test
        void 해당_상품의_꿀조합_목록을_좋아요가_많은_순으로_조회할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));

            RecipeSteps.레시피_작성_요청(로그인_쿠키_획득(1L), List.of(사진_명세_요청("1")), 레시피추가요청_생성(1L));
            RecipeSteps.레시피_작성_요청(로그인_쿠키_획득(1L), List.of(사진_명세_요청("2")), 레시피추가요청_생성(1L));
            RecipeSteps.레시피_작성_요청(로그인_쿠키_획득(1L), List.of(사진_명세_요청("3")), 레시피추가요청_생성(1L));
            레시피_좋아요_요청(로그인_쿠키_획득(1L), 1L, 레시피좋아요요청_생성(true));
            레시피_좋아요_요청(로그인_쿠키_획득(2L), 2L, 레시피좋아요요청_생성(true));
            레시피_좋아요_요청(로그인_쿠키_획득(3L), 2L, 레시피좋아요요청_생성(true));

            final var pageDto = new PageDto(3L, 1L, true, true, 0L, 10L);

            // when
            final var response = 상품_레시피_목록_요청(1L, 좋아요수_내림차순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            상품_레시피_목록_조회_결과를_검증한다(response, List.of(2L, 1L, 3L));
        }

        @Test
        void 해당_상품의_꿀조합_목록을_최신순으로_조회할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));
            단일_상품_저장(상품_삼각김밥_가격2000원_평점3점_생성(category));
            단일_상품_저장(상품_삼각김밥_가격2000원_평점1점_생성(category));

            RecipeSteps.레시피_작성_요청(로그인_쿠키_획득(1L), List.of(사진_명세_요청("1")), 레시피추가요청_생성(1L));
            RecipeSteps.레시피_작성_요청(로그인_쿠키_획득(1L), List.of(사진_명세_요청("2")), 레시피추가요청_생성(1L));
            RecipeSteps.레시피_작성_요청(로그인_쿠키_획득(1L), List.of(사진_명세_요청("3")), 레시피추가요청_생성(1L));

            final var pageDto = new PageDto(3L, 1L, true, true, 0L, 10L);

            // when
            final var response = 상품_레시피_목록_요청(1L, 최신순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            상품_레시피_목록_조회_결과를_검증한다(response, List.of(3L, 2L, 1L));
        }

        @Test
        void 해당_상품의_꿀조합_목록을_오래된순으로_조회할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            단일_상품_저장(상품_삼각김밥_가격1000원_평점5점_생성(category));
            단일_상품_저장(상품_삼각김밥_가격2000원_평점3점_생성(category));
            단일_상품_저장(상품_삼각김밥_가격2000원_평점1점_생성(category));

            RecipeSteps.레시피_작성_요청(로그인_쿠키_획득(1L), List.of(사진_명세_요청("1")), 레시피추가요청_생성(1L));
            RecipeSteps.레시피_작성_요청(로그인_쿠키_획득(1L), List.of(사진_명세_요청("2")), 레시피추가요청_생성(1L));
            RecipeSteps.레시피_작성_요청(로그인_쿠키_획득(1L), List.of(사진_명세_요청("3")), 레시피추가요청_생성(1L));

            final var pageDto = new PageDto(3L, 1L, true, true, 0L, 10L);

            // when
            final var response = 상품_레시피_목록_요청(1L, 과거순, FIRST_PAGE);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            상품_레시피_목록_조회_결과를_검증한다(response, List.of(1L, 2L, 3L));
        }
    }

    private void 페이지를_검증한다(final ExtractableResponse<Response> response, final PageDto expected) {
        final var actual = response.jsonPath().getObject("page", PageDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
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
            soft.assertThat(actualTags).extracting("id").containsExactly(2L, 3L, 1L);
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
