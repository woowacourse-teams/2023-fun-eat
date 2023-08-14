package com.funeat.acceptance.product;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키를_얻는다;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.common.CommonSteps.찾을수_없음;
import static com.funeat.acceptance.product.ProductSteps.상품_검색_결과_조회_요청;
import static com.funeat.acceptance.product.ProductSteps.상품_랭킹_조회_요청;
import static com.funeat.acceptance.product.ProductSteps.상품_상세_조회_요청;
import static com.funeat.acceptance.product.ProductSteps.상품_자동_완성_검색_요청;
import static com.funeat.acceptance.product.ProductSteps.카테고리별_상품_목록_조회_요청;
import static com.funeat.acceptance.review.ReviewSteps.단일_리뷰_요청;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_사진_명세_요청;
import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버3_생성;
import static com.funeat.fixture.ProductFixture.상품_망고빙수_가격5000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점2점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격4000원_평점2점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격4000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격5000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격5000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격5000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_애플망고_가격3000원_평점5점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test1_평점1점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test2_평점2점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test4_평점4점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test4_평점4점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test5_평점5점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매X_생성;
import static com.funeat.fixture.TagFixture.태그_간식_ETC_생성;
import static com.funeat.fixture.TagFixture.태그_단짠단짠_TASTE_생성;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static com.funeat.product.exception.CategoryErrorCode.CATEGORY_NOT_FOUND;
import static com.funeat.product.exception.ProductErrorCode.PRODUCT_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.common.dto.PageDto;
import com.funeat.product.domain.Product;
import com.funeat.product.dto.ProductInCategoryDto;
import com.funeat.product.dto.ProductResponse;
import com.funeat.product.dto.RankingProductDto;
import com.funeat.product.dto.SearchProductDto;
import com.funeat.product.dto.SearchProductResultDto;
import com.funeat.tag.domain.Tag;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ProductAcceptanceTest extends AcceptanceTest {

    private static final Long PAGE_SIZE = 10L;
    private static final Long FIRST_PAGE = 0L;

    @Nested
    class getAllProductsInCategory_성공_테스트 {

        @Nested
        class 가격_기준_내림차순으로_카테고리별_상품_목록_조회 {

            @Test
            void 상품_가격이_서로_다르면_가격_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                final var categoryId = 단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
                final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
                final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
                final var product4 = 상품_삼각김밥_가격4000원_평점4점_생성(category);
                final var product5 = 상품_삼각김밥_가격5000원_평점4점_생성(category);
                final var product6 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
                final var product7 = 상품_삼각김밥_가격4000원_평점2점_생성(category);
                final var product8 = 상품_삼각김밥_가격5000원_평점1점_생성(category);
                final var product9 = 상품_삼각김밥_가격3000원_평점3점_생성(category);
                final var product10 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
                final var product11 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                복수_상품_저장(product1, product2, product3, product4, product5, product6, product7, product8, product9,
                        product10, product11);

                final var pageDto = new PageDto(11L, 2L, true, false, FIRST_PAGE, PAGE_SIZE);

                final var expectedProducts = List.of(product8, product5, product7, product4, product10, product9,
                        product3, product2, product11, product6);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(categoryId, "price", "desc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, expectedProducts);
            }

            @Test
            void 상품_가격이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                final var categoryId = 단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
                final var product2 = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var product3 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product4 = 상품_삼각김밥_가격1000원_평점4점_생성(category);
                final var product5 = 상품_삼각김밥_가격1000원_평점4점_생성(category);
                final var product6 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
                final var product7 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
                final var product8 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product9 = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var product10 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product11 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                복수_상품_저장(product1, product2, product3, product4, product5, product6, product7, product8, product9,
                        product10, product11);

                final var pageDto = new PageDto(11L, 2L, true, false, FIRST_PAGE, PAGE_SIZE);

                final var expectedProducts = List.of(product11, product10, product9, product8, product7, product6,
                        product5, product4, product3, product2);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(categoryId, "price", "desc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, expectedProducts);
            }
        }

        @Nested
        class 가격_기준_오름차순으로_카테고리별_상품_목록_조회 {

            @Test
            void 상품_가격이_서로_다르면_가격_기준_오름차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                final var categoryId = 단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
                final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
                final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
                final var product4 = 상품_삼각김밥_가격4000원_평점4점_생성(category);
                final var product5 = 상품_삼각김밥_가격5000원_평점4점_생성(category);
                final var product6 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
                final var product7 = 상품_삼각김밥_가격4000원_평점2점_생성(category);
                final var product8 = 상품_삼각김밥_가격5000원_평점1점_생성(category);
                final var product9 = 상품_삼각김밥_가격3000원_평점3점_생성(category);
                final var product10 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
                final var product11 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                복수_상품_저장(product1, product2, product3, product4, product5, product6, product7, product8, product9,
                        product10, product11);

                final var pageDto = new PageDto(11L, 2L, true, false, FIRST_PAGE, PAGE_SIZE);

                final var expectedProducts = List.of(product11, product6, product1, product3, product2, product10,
                        product9, product7, product4, product8);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(categoryId, "price", "asc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, expectedProducts);
            }

            @Test
            void 상품_가격이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                final var categoryId = 단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
                final var product2 = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var product3 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product4 = 상품_삼각김밥_가격1000원_평점4점_생성(category);
                final var product5 = 상품_삼각김밥_가격1000원_평점4점_생성(category);
                final var product6 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
                final var product7 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
                final var product8 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product9 = 상품_삼각김밥_가격1000원_평점3점_생성(category);
                final var product10 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product11 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                복수_상품_저장(product1, product2, product3, product4, product5, product6, product7, product8, product9,
                        product10, product11);

                final var pageDto = new PageDto(11L, 2L, true, false, FIRST_PAGE, PAGE_SIZE);

                final var expectedProducts = List.of(product11, product10, product9, product8, product7, product6,
                        product5, product4, product3, product2);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(categoryId, "price", "asc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, expectedProducts);
            }
        }

        @Nested
        class 평점_기준_내림차순으로_카테고리별_상품_목록_조회 {

            @Test
            void 상품_평점이_서로_다르면_평점_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                final var categoryId = 단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
                final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
                final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
                final var product4 = 상품_삼각김밥_가격1000원_평점4점_생성(category);
                final var product5 = 상품_삼각김밥_가격2000원_평점4점_생성(category);
                final var product6 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
                final var product7 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
                final var product8 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product9 = 상품_삼각김밥_가격3000원_평점3점_생성(category);
                final var product10 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
                final var product11 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                복수_상품_저장(product1, product2, product3, product4, product5, product6, product7, product8, product9,
                        product10, product11);

                final var pageDto = new PageDto(11L, 2L, true, false, FIRST_PAGE, PAGE_SIZE);

                final var expectedProducts = List.of(product1, product5, product4, product9, product2, product7,
                        product6, product11, product10, product8);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(categoryId, "averageRating", "desc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, expectedProducts);
            }

            @Test
            void 상품_평점이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                final var categoryId = 단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product2 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
                final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
                final var product4 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product5 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
                final var product6 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product7 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product8 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product9 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
                final var product10 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
                final var product11 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                복수_상품_저장(product1, product2, product3, product4, product5, product6, product7, product8, product9,
                        product10, product11);

                final var pageDto = new PageDto(11L, 2L, true, false, FIRST_PAGE, PAGE_SIZE);

                final var expectedProducts = List.of(product11, product10, product9, product8, product7, product6,
                        product5, product4, product3, product2);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(categoryId, "averageRating", "desc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, expectedProducts);
            }
        }

        @Nested
        class 평점_기준_오름차순으로_카테고리별_상품_목록_조회 {

            @Test
            void 상품_평점이_서로_다르면_평점_기준_오름차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                final var categoryId = 단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
                final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
                final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
                final var product4 = 상품_삼각김밥_가격1000원_평점4점_생성(category);
                final var product5 = 상품_삼각김밥_가격2000원_평점4점_생성(category);
                final var product6 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
                final var product7 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
                final var product8 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product9 = 상품_삼각김밥_가격3000원_평점3점_생성(category);
                final var product10 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
                final var product11 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                복수_상품_저장(product1, product2, product3, product4, product5, product6, product7, product8, product9,
                        product10, product11);

                final var pageDto = new PageDto(11L, 2L, true, false, FIRST_PAGE, PAGE_SIZE);

                final var expectedProducts = List.of(product11, product10, product8, product3, product7, product6,
                        product9, product2, product5, product4);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(categoryId, "averageRating", "asc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, expectedProducts);
            }

            @Test
            void 상품_평점이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                final var categoryId = 단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product2 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
                final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
                final var product4 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product5 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
                final var product6 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product7 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product8 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                final var product9 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
                final var product10 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
                final var product11 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
                복수_상품_저장(product1, product2, product3, product4, product5, product6, product7, product8, product9,
                        product10, product11);

                final var pageDto = new PageDto(11L, 2L, true, false, FIRST_PAGE, PAGE_SIZE);

                final var expectedProducts = List.of(product11, product10, product9, product8, product7, product6,
                        product5, product4, product3, product2);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(categoryId, "averageRating", "asc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, expectedProducts);
            }
        }

        @Nested
        class 리뷰수_기준_내림차순으로_카테고리별_상품_목록_조회 {

            @Test
            void 리뷰수가_서로_다르면_리뷰수_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                final var categoryId = 단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
                final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
                final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
                복수_상품_저장(product1, product2, product3);

                final var member1 = 멤버_멤버1_생성();
                final var member2 = 멤버_멤버2_생성();
                final var member3 = 멤버_멤버3_생성();
                복수_멤버_저장(member1, member2, member3);

                final var review1_1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product1, 0L);
                final var review1_2 = 리뷰_이미지test3_평점3점_재구매O_생성(member2, product1, 0L);
                final var review1_3 = 리뷰_이미지test4_평점4점_재구매O_생성(member3, product1, 0L);
                final var review2_1 = 리뷰_이미지test1_평점1점_재구매X_생성(member1, product2, 0L);
                final var review2_2 = 리뷰_이미지test1_평점1점_재구매X_생성(member2, product2, 0L);
                final var review3_1 = 리뷰_이미지test2_평점2점_재구매X_생성(member1, product3, 0L);
                final var review3_2 = 리뷰_이미지test2_평점2점_재구매X_생성(member2, product3, 0L);
                final var review3_3 = 리뷰_이미지test2_평점2점_재구매X_생성(member3, product3, 0L);
                복수_리뷰_저장(review1_1, review1_2, review1_3, review2_1, review2_2, review3_1, review3_2, review3_3);

                final var pageDto = new PageDto(3L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                final var expectedProducts = List.of(product3, product1, product2);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(categoryId, "reviewCount", "desc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, expectedProducts);
            }

            @Test
            void 리뷰수가_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
                // given
                final var category = 카테고리_간편식사_생성();
                final var categoryId = 단일_카테고리_저장(category);

                final var product1 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
                final var product2 = 상품_삼각김밥_가격5000원_평점3점_생성(category);
                final var product3 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
                final var product4 = 상품_삼각김밥_가격2000원_평점4점_생성(category);
                final var product5 = 상품_삼각김밥_가격3000원_평점4점_생성(category);
                복수_상품_저장(product1, product2, product3, product4, product5);

                final var member1 = 멤버_멤버1_생성();
                단일_멤버_저장(member1);

                final var review1_1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product1, 0L);
                final var review2_1 = 리뷰_이미지test1_평점1점_재구매X_생성(member1, product2, 0L);
                final var review3_1 = 리뷰_이미지test2_평점2점_재구매X_생성(member1, product3, 0L);
                복수_리뷰_저장(review1_1, review2_1, review3_1);

                final var pageDto = new PageDto(5L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

                final var expectedProducts = List.of(product3, product2, product1, product5, product4);

                // when
                final var response = 카테고리별_상품_목록_조회_요청(categoryId, "reviewCount", "desc", 0);

                // then
                STATUS_CODE를_검증한다(response, 정상_처리);
                페이지를_검증한다(response, pageDto);
                카테고리별_상품_목록_조회_결과를_검증한다(response, expectedProducts);
            }
        }
    }

    @Nested
    class getAllProductsInCategory_실패_테스트 {

        @Test
        void 상품을_정렬할때_카테고리가_존재하지_않으면_예외가_발생한다() {
            // given
            final var notExistCategoryId = 99999L;

            // when
            final var response = 카테고리별_상품_목록_조회_요청(notExistCategoryId, "price", "desc", 0);

            // then
            STATUS_CODE를_검증한다(response, 찾을수_없음);
            RESPONSE_CODE와_MESSAGE를_검증한다(response, CATEGORY_NOT_FOUND.getCode(), CATEGORY_NOT_FOUND.getMessage());
        }
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

    @Nested
    class getProductDetail_성공_테스트 {

        @Test
        void 상품_상세_정보를_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_단짠단짠_TASTE_생성();
            final var tag3 = 태그_간식_ETC_생성();
            복수_태그_저장(tag1, tag2, tag3);

            final var image = 리뷰_사진_명세_요청();

            final var request1 = 리뷰추가요청_재구매X_생성(4L, 태그_아이디_변환(tag1, tag2, tag3));
            final var request2 = 리뷰추가요청_재구매X_생성(4L, 태그_아이디_변환(tag2, tag3));
            final var request3 = 리뷰추가요청_재구매X_생성(3L, 태그_아이디_변환(tag2));

            final var loginCookie = 로그인_쿠키를_얻는다();

            단일_리뷰_요청(productId, image, request1, loginCookie);
            단일_리뷰_요청(productId, image, request2, loginCookie);
            단일_리뷰_요청(productId, image, request3, loginCookie);

            final var expectedReviewCount = 3L;
            final var expectedTags = List.of(tag2, tag3, tag1);

            // when
            final var response = 상품_상세_조회_요청(productId);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            상품_상세_정보_조회_결과를_검증한다(response, product, expectedReviewCount, expectedTags);
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
            RESPONSE_CODE와_MESSAGE를_검증한다(response, PRODUCT_NOT_FOUND.getCode(), PRODUCT_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class getRankingProducts_성공_테스트 {

        @Test
        void 전체_상품들_중에서_랭킹_TOP3를_조회할_수_있다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점4점_생성(category);
            final var product2 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
            final var product3 = 상품_삼각김밥_가격1000원_평점4점_생성(category);
            final var product4 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3, product4);

            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            final var member3 = 멤버_멤버3_생성();
            복수_멤버_저장(member1, member2, member3);

            final var review1_1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product1, 0L);
            final var review1_2 = 리뷰_이미지test3_평점3점_재구매O_생성(member2, product1, 0L);
            final var review1_3 = 리뷰_이미지test4_평점4점_재구매O_생성(member3, product1, 0L);
            final var review2_1 = 리뷰_이미지test4_평점4점_재구매X_생성(member1, product2, 0L);
            final var review2_2 = 리뷰_이미지test4_평점4점_재구매X_생성(member2, product2, 0L);
            final var review3_1 = 리뷰_이미지test2_평점2점_재구매X_생성(member1, product3, 0L);
            final var review3_2 = 리뷰_이미지test5_평점5점_재구매X_생성(member2, product3, 0L);
            복수_리뷰_저장(review1_1, review1_2, review1_3, review2_1, review2_2, review3_1, review3_2);

            final var expectedProduct = List.of(product2, product1, product3);

            // when
            final var response = 상품_랭킹_조회_요청();

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            상품_랭킹_조회_결과를_검증한다(response, expectedProduct);
        }
    }

    @Nested
    class searchProducts_성공_테스트 {

        @Test
        void 검색어가_포함된_상품들을_검색한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_애플망고_가격3000원_평점5점_생성(category);
            final var product2 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            복수_상품_저장(product1, product2);

            final var pageDto = new PageDto(2L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);
            final var expectedProducts = List.of(product2, product1);

            // when
            final var response = 상품_자동_완성_검색_요청("망고", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            상품_자동_완성_검색_결과를_검증한다(response, expectedProducts);
        }

        @Test
        void 검색어가_포함된_상품이_없으면_빈_리스트를_반환한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_애플망고_가격3000원_평점5점_생성(category);
            final var product2 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            복수_상품_저장(product1, product2);

            final var pageDto = new PageDto(0L, 0L, true, true, FIRST_PAGE, PAGE_SIZE);
            final var expectedProducts = Collections.emptyList();

            // when
            final var response = 상품_자동_완성_검색_요청("김밥", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            상품_자동_완성_검색_결과를_검증한다(response, expectedProducts);
        }
    }

    @Nested
    class getSearchResults_성공_테스트 {

        @Test
        void 상품_검색_결과들을_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_애플망고_가격3000원_평점5점_생성(category);
            final var product2 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            복수_상품_저장(product1, product2);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_간식_ETC_생성();
            복수_태그_저장(tag1, tag2);

            final var image = 리뷰_사진_명세_요청();

            final var request1 = 리뷰추가요청_재구매X_생성(5L, 태그_아이디_변환(tag1, tag2));
            final var request2 = 리뷰추가요청_재구매X_생성(5L, 태그_아이디_변환(tag1));
            final var request3 = 리뷰추가요청_재구매X_생성(4L, 태그_아이디_변환(tag2));

            final var loginCookie = 로그인_쿠키를_얻는다();

            단일_리뷰_요청(product1.getId(), image, request1, loginCookie);
            단일_리뷰_요청(product1.getId(), image, request2, loginCookie);
            단일_리뷰_요청(product2.getId(), image, request3, loginCookie);

            final var pageDto = new PageDto(2L, 1L, true, true, FIRST_PAGE, PAGE_SIZE);

            final var expectedDto1 = SearchProductResultDto.toDto(product1, 2L);
            final var expectedDto2 = SearchProductResultDto.toDto(product2, 1L);
            final var expected = List.of(expectedDto2, expectedDto1);

            // when
            final var response = 상품_검색_결과_조회_요청("망고", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            상품_검색_결과를_검증한다(response, expected);
        }

        @Test
        void 검색_결과에_상품이_없으면_빈_리스트를_반환한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_애플망고_가격3000원_평점5점_생성(category);
            final var product2 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            복수_상품_저장(product1, product2);

            final var pageDto = new PageDto(0L, 0L, true, true, FIRST_PAGE, PAGE_SIZE);
            final var expected = Collections.emptyList();

            // when
            final var response = 상품_검색_결과_조회_요청("김밥", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, pageDto);
            상품_검색_결과를_검증한다(response, expected);
        }
    }

    private List<Long> 태그_아이디_변환(final Tag... tags) {
        return Arrays.stream(tags)
                .map(Tag::getId)
                .collect(Collectors.toList());
    }

    private void 페이지를_검증한다(final ExtractableResponse<Response> response, final PageDto expected) {
        final var actual = response.jsonPath().getObject("page", PageDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private void 카테고리별_상품_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Product> products) {
        final var expected = products.stream()
                .map(product -> ProductInCategoryDto.toDto(product, 0L))
                .collect(Collectors.toList());
        final var actual = response.jsonPath()
                .getList("products", ProductInCategoryDto.class);

        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("reviewCount")
                .isEqualTo(expected);
    }

    private void 상품_상세_정보_조회_결과를_검증한다(final ExtractableResponse<Response> response, final Product product,
                                      final Long expectedReviewCount, final List<Tag> expectedTags) {
        final var expected = ProductResponse.toResponse(product, expectedReviewCount, expectedTags);
        final var actual = response.as(ProductResponse.class);

        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("averageRating")
                .isEqualTo(expected);
    }

    private void 상품_랭킹_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Product> products) {
        final var expected = products.stream()
                .map(RankingProductDto::toDto)
                .collect(Collectors.toList());
        final var actual = response.jsonPath()
                .getList("products", RankingProductDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private <T> void 상품_자동_완성_검색_결과를_검증한다(final ExtractableResponse<Response> response, final List<T> products) {
        final var expected = products.stream()
                .map(product -> SearchProductDto.toDto((Product) product))
                .collect(Collectors.toList());
        final var actual = response.jsonPath()
                .getList("products", SearchProductDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private <T> void 상품_검색_결과를_검증한다(final ExtractableResponse<Response> response, final List<T> expected) {
        final var actual = response.jsonPath()
                .getList("products", SearchProductResultDto.class);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
