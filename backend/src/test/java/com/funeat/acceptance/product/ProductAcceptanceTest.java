package com.funeat.acceptance.product;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키를_얻는다;
import static com.funeat.acceptance.common.CommonSteps.STATUS_CODE를_검증한다;
import static com.funeat.acceptance.common.CommonSteps.정상_처리;
import static com.funeat.acceptance.product.ProductSteps.상품_랭킹_조회_요청;
import static com.funeat.acceptance.product.ProductSteps.상품_상세_조회_요청;
import static com.funeat.acceptance.product.ProductSteps.카테고리별_상품_목록_조회_요청;
import static com.funeat.acceptance.review.ReviewSteps.단일_리뷰_저장;
import static com.funeat.acceptance.review.ReviewSteps.리뷰_사진_명세_요청;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.acceptance.common.AcceptanceTest;
import com.funeat.member.domain.Member;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.domain.Product;
import com.funeat.product.dto.ProductInCategoryDto;
import com.funeat.product.dto.ProductResponse;
import com.funeat.product.dto.ProductsInCategoryPageDto;
import com.funeat.product.dto.RankingProductDto;
import com.funeat.review.domain.Review;
import com.funeat.review.presentation.dto.ReviewCreateRequest;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.domain.TagType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ProductAcceptanceTest extends AcceptanceTest {

    public static final int PAGE_SIZE = 10;

    @Nested
    class 가격_기준_내림차순으로_카테고리별_상품_목록_조회 {

        @Test
        void 상품_가격이_서로_다르면_가격_기준_내림차순으로_정렬할_수_있다() {
            // given
            final var 간편식사 = new Category("간편식사", CategoryType.FOOD);
            final var categoryId = 단일_카테고리_저장(간편식사);
            final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 간편식사);
            final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 간편식사);
            final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 간편식사);
            final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", 간편식사);
            final var product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", 간편식사);
            final var product6 = new Product("삼각김밥6", 1700L, "image.png", "맛있는 삼각김밥6", 간편식사);
            final var product7 = new Product("삼각김밥7", 1800L, "image.png", "맛있는 삼각김밥7", 간편식사);
            final var product8 = new Product("삼각김밥8", 800L, "image.png", "맛있는 삼각김밥8", 간편식사);
            final var product9 = new Product("삼각김밥9", 3100L, "image.png", "맛있는 삼각김밥9", 간편식사);
            final var product10 = new Product("삼각김밥10", 2700L, "image.png", "맛있는 삼각김밥10", 간편식사);
            final var product11 = new Product("삼각김밥11", 300L, "image.png", "맛있는 삼각김밥11", 간편식사);
            final var products = List.of(product1, product2, product3, product4, product5, product6, product7,
                    product8, product9, product10, product11);
            복수_상품_저장(products);

            // when
            final var response = 카테고리별_상품_목록_조회_요청(categoryId, "price", "asc", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, (long) products.size(), 0L);
            카테고리별_상품_목록_조회_결과를_검증한다(response,
                    List.of(product11, product8, product1, product4, product3, product6, product7, product2, product5,
                            product10));
        }

        @Test
        void 상품_가격이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
            // given
            final var 간편식사 = new Category("간편식사", CategoryType.FOOD);
            final var categoryId = 단일_카테고리_저장(간편식사);
            final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 간편식사);
            final var product2 = new Product("삼각김밥2", 1000L, "image.png", "맛있는 삼각김밥2", 간편식사);
            final var product3 = new Product("삼각김밥3", 1000L, "image.png", "맛있는 삼각김밥3", 간편식사);
            final var product4 = new Product("삼각김밥4", 1000L, "image.png", "맛있는 삼각김밥4", 간편식사);
            final var product5 = new Product("삼각김밥5", 1000L, "image.png", "맛있는 삼각김밥5", 간편식사);
            final var product6 = new Product("삼각김밥6", 1000L, "image.png", "맛있는 삼각김밥6", 간편식사);
            final var product7 = new Product("삼각김밥7", 1000L, "image.png", "맛있는 삼각김밥7", 간편식사);
            final var product8 = new Product("삼각김밥8", 1000L, "image.png", "맛있는 삼각김밥8", 간편식사);
            final var product9 = new Product("삼각김밥9", 1000L, "image.png", "맛있는 삼각김밥9", 간편식사);
            final var product10 = new Product("삼각김밥10", 1000L, "image.png", "맛있는 삼각김밥10", 간편식사);
            final var product11 = new Product("삼각김밥11", 1000L, "image.png", "맛있는 삼각김밥11", 간편식사);
            final var products = List.of(product1, product2, product3, product4, product5, product6, product7,
                    product8, product9, product10, product11);
            복수_상품_저장(products);

            // when
            final var response = 카테고리별_상품_목록_조회_요청(categoryId, "price", "asc", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, (long) products.size(), 0L);
            카테고리별_상품_목록_조회_결과를_검증한다(response,
                    List.of(product11, product10, product9, product8, product7, product6, product5, product4, product3,
                            product2));
        }
    }

    @Nested
    class 가격_기준_오름차순으로_카테고리별_상품_목록_조회 {

        @Test
        void 상품_가격이_서로_다르면_가격_기준_오름차순으로_정렬할_수_있다() {
            // given
            final var 간편식사 = new Category("간편식사", CategoryType.FOOD);
            final var categoryId = 단일_카테고리_저장(간편식사);
            final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 간편식사);
            final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 간편식사);
            final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 간편식사);
            final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", 간편식사);
            final var product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", 간편식사);
            final var product6 = new Product("삼각김밥6", 1700L, "image.png", "맛있는 삼각김밥6", 간편식사);
            final var product7 = new Product("삼각김밥7", 1800L, "image.png", "맛있는 삼각김밥7", 간편식사);
            final var product8 = new Product("삼각김밥8", 800L, "image.png", "맛있는 삼각김밥8", 간편식사);
            final var product9 = new Product("삼각김밥9", 3100L, "image.png", "맛있는 삼각김밥9", 간편식사);
            final var product10 = new Product("삼각김밥10", 2700L, "image.png", "맛있는 삼각김밥10", 간편식사);
            final var product11 = new Product("삼각김밥11", 300L, "image.png", "맛있는 삼각김밥11", 간편식사);
            final var products = List.of(product1, product2, product3, product4, product5, product6, product7,
                    product8, product9, product10, product11);
            복수_상품_저장(products);

            // when
            final var response = 카테고리별_상품_목록_조회_요청(categoryId, "price", "desc", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, (long) products.size(), 0L);
            카테고리별_상품_목록_조회_결과를_검증한다(response,
                    List.of(product9, product10, product5, product2, product7, product6, product3, product4, product1,
                            product8));
        }

        @Test
        void 상품_가격이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
            // given
            final var 간편식사 = new Category("간편식사", CategoryType.FOOD);
            final var categoryId = 단일_카테고리_저장(간편식사);
            final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 간편식사);
            final var product2 = new Product("삼각김밥2", 1000L, "image.png", "맛있는 삼각김밥2", 간편식사);
            final var product3 = new Product("삼각김밥3", 1000L, "image.png", "맛있는 삼각김밥3", 간편식사);
            final var product4 = new Product("삼각김밥4", 1000L, "image.png", "맛있는 삼각김밥4", 간편식사);
            final var product5 = new Product("삼각김밥5", 1000L, "image.png", "맛있는 삼각김밥5", 간편식사);
            final var product6 = new Product("삼각김밥6", 1000L, "image.png", "맛있는 삼각김밥6", 간편식사);
            final var product7 = new Product("삼각김밥7", 1000L, "image.png", "맛있는 삼각김밥7", 간편식사);
            final var product8 = new Product("삼각김밥8", 1000L, "image.png", "맛있는 삼각김밥8", 간편식사);
            final var product9 = new Product("삼각김밥9", 1000L, "image.png", "맛있는 삼각김밥9", 간편식사);
            final var product10 = new Product("삼각김밥10", 1000L, "image.png", "맛있는 삼각김밥10", 간편식사);
            final var product11 = new Product("삼각김밥11", 1000L, "image.png", "맛있는 삼각김밥11", 간편식사);
            final var products = List.of(product1, product2, product3, product4, product5, product6, product7,
                    product8, product9, product10, product11);
            복수_상품_저장(products);

            // when
            final var response = 카테고리별_상품_목록_조회_요청(categoryId, "price", "desc", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, (long) products.size(), 0L);
            카테고리별_상품_목록_조회_결과를_검증한다(response,
                    List.of(product11, product10, product9, product8, product7, product6, product5, product4, product3,
                            product2));
        }
    }

    @Nested
    class 평점_기준_내림차순으로_카테고리별_상품_목록_조회 {
        @Test
        void 상품_평점이_서로_다르면_평점_기준_내림차순으로_정렬할_수_있다() {
            // given
            final var 간편식사 = new Category("간편식사", CategoryType.FOOD);
            final var categoryId = 단일_카테고리_저장(간편식사);
            final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 5.0, 간편식사);
            final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 3.0, 간편식사);
            final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 1.0, 간편식사);
            final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", 4.0, 간편식사);
            final var product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", 4.5, 간편식사);
            final var product6 = new Product("삼각김밥6", 1700L, "image.png", "맛있는 삼각김밥6", 2.5, 간편식사);
            final var product7 = new Product("삼각김밥7", 1800L, "image.png", "맛있는 삼각김밥7", 2.0, 간편식사);
            final var product8 = new Product("삼각김밥8", 800L, "image.png", "맛있는 삼각김밥8", 1.5, 간편식사);
            final var product9 = new Product("삼각김밥9", 3100L, "image.png", "맛있는 삼각김밥9", 3.5, 간편식사);
            final var product10 = new Product("삼각김밥10", 2700L, "image.png", "맛있는 삼각김밥10", 0.0, 간편식사);
            final var product11 = new Product("삼각김밥11", 300L, "image.png", "맛있는 삼각김밥11", 0.5, 간편식사);
            final var products = List.of(product1, product2, product3, product4, product5, product6, product7,
                    product8, product9, product10, product11);
            복수_상품_저장(products);

            // when
            final var response = 카테고리별_상품_목록_조회_요청(categoryId, "averageRating", "desc", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, (long) products.size(), 0L);
            카테고리별_상품_목록_조회_결과를_검증한다(response,
                    List.of(product1, product5, product4, product9, product2, product6, product7, product8, product3,
                            product11));
        }

        @Test
        void 상품_평점이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
            // given
            final var 간편식사 = new Category("간편식사", CategoryType.FOOD);
            final var categoryId = 단일_카테고리_저장(간편식사);
            final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 5.0, 간편식사);
            final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 3.0, 간편식사);
            final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 1.0, 간편식사);
            final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", 4.0, 간편식사);
            final var product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", 4.5, 간편식사);
            final var product6 = new Product("삼각김밥6", 1700L, "image.png", "맛있는 삼각김밥6", 2.5, 간편식사);
            final var product7 = new Product("삼각김밥7", 1800L, "image.png", "맛있는 삼각김밥7", 2.0, 간편식사);
            final var product8 = new Product("삼각김밥8", 800L, "image.png", "맛있는 삼각김밥8", 1.5, 간편식사);
            final var product9 = new Product("삼각김밥9", 3100L, "image.png", "맛있는 삼각김밥9", 3.5, 간편식사);
            final var product10 = new Product("삼각김밥10", 2700L, "image.png", "맛있는 삼각김밥10", 1.0, 간편식사);
            final var product11 = new Product("삼각김밥11", 300L, "image.png", "맛있는 삼각김밥11", 0.5, 간편식사);
            final var products = List.of(product1, product2, product3, product4, product5, product6, product7,
                    product8, product9, product10, product11);
            복수_상품_저장(products);

            // when
            final var response = 카테고리별_상품_목록_조회_요청(categoryId, "averageRating", "desc", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, (long) products.size(), 0L);
            카테고리별_상품_목록_조회_결과를_검증한다(response,
                    List.of(product1, product5, product4, product9, product2, product6, product7, product8, product10,
                            product3));
        }
    }

    @Nested
    class 평점_기준_오름차순으로_카테고리별_상품_목록_조회 {
        @Test
        void 상품_평점이_서로_다르면_평점_기준_오름차순으로_정렬할_수_있다() {
            // given
            final var 간편식사 = new Category("간편식사", CategoryType.FOOD);
            final var categoryId = 단일_카테고리_저장(간편식사);
            final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 5.0, 간편식사);
            final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 3.0, 간편식사);
            final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 1.0, 간편식사);
            final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", 4.0, 간편식사);
            final var product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", 4.5, 간편식사);
            final var product6 = new Product("삼각김밥6", 1700L, "image.png", "맛있는 삼각김밥6", 2.5, 간편식사);
            final var product7 = new Product("삼각김밥7", 1800L, "image.png", "맛있는 삼각김밥7", 2.0, 간편식사);
            final var product8 = new Product("삼각김밥8", 800L, "image.png", "맛있는 삼각김밥8", 1.5, 간편식사);
            final var product9 = new Product("삼각김밥9", 3100L, "image.png", "맛있는 삼각김밥9", 3.5, 간편식사);
            final var product10 = new Product("삼각김밥10", 2700L, "image.png", "맛있는 삼각김밥10", 0.0, 간편식사);
            final var product11 = new Product("삼각김밥11", 300L, "image.png", "맛있는 삼각김밥11", 0.5, 간편식사);
            final var products = List.of(product1, product2, product3, product4, product5, product6, product7,
                    product8, product9, product10, product11);
            복수_상품_저장(products);

            // when
            final var response = 카테고리별_상품_목록_조회_요청(categoryId, "averageRating", "asc", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, (long) products.size(), 0L);
            카테고리별_상품_목록_조회_결과를_검증한다(response,
                    List.of(product10, product11, product3, product8, product7, product6, product2, product9, product4,
                            product5));
        }

        @Test
        void 상품_평점이_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
            // given
            final var 간편식사 = new Category("간편식사", CategoryType.FOOD);
            final var categoryId = 단일_카테고리_저장(간편식사);
            final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 5.0, 간편식사);
            final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 3.0, 간편식사);
            final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 1.0, 간편식사);
            final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", 4.0, 간편식사);
            final var product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", 4.5, 간편식사);
            final var product6 = new Product("삼각김밥6", 1700L, "image.png", "맛있는 삼각김밥6", 2.5, 간편식사);
            final var product7 = new Product("삼각김밥7", 1800L, "image.png", "맛있는 삼각김밥7", 2.0, 간편식사);
            final var product8 = new Product("삼각김밥8", 800L, "image.png", "맛있는 삼각김밥8", 1.5, 간편식사);
            final var product9 = new Product("삼각김밥9", 3100L, "image.png", "맛있는 삼각김밥9", 3.5, 간편식사);
            final var product10 = new Product("삼각김밥10", 2700L, "image.png", "맛있는 삼각김밥10", 1.0, 간편식사);
            final var product11 = new Product("삼각김밥11", 300L, "image.png", "맛있는 삼각김밥11", 0.5, 간편식사);
            final var products = List.of(product1, product2, product3, product4, product5, product6, product7,
                    product8, product9, product10, product11);
            복수_상품_저장(products);

            // when
            final var response = 카테고리별_상품_목록_조회_요청(categoryId, "averageRating", "asc", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, (long) products.size(), 0L);
            카테고리별_상품_목록_조회_결과를_검증한다(response,
                    List.of(product11, product10, product3, product8, product7, product6, product2, product9, product4,
                            product5));
        }
    }

    @Nested
    class 리뷰수_기준_내림차순으로_카테고리별_상품_목록_조회 {
        @Test
        void 리뷰수가_서로_다르면_리뷰수_기준_내림차순으로_정렬할_수_있다() {
            // given
            final var 간편식사 = new Category("간편식사", CategoryType.FOOD);
            final var categoryId = 단일_카테고리_저장(간편식사);
            final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 5.0, 간편식사);
            final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 3.0, 간편식사);
            final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 1.0, 간편식사);
            final var products = List.of(product1, product2, product3);
            복수_상품_저장(products);

            final var member = new Member("test", "image.png", "1");
            단일_멤버_저장(member);
            final var review1_1 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true);
            final var review1_2 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true);
            final var review1_3 = new Review(member, product1, "review.png", 4L, "이 삼각김밥은 좀 맛있다", true);
            final var review1_4 = new Review(member, product1, "review.png", 5L, "이 삼각김밥은 최고!!", true);
            final var review2_1 = new Review(member, product2, "review.png", 1L, "이 삼각김밥은 맛없다", false);
            final var review2_2 = new Review(member, product2, "review.png", 1L, "이 삼각김밥은 맛없다", false);
            final var review3_1 = new Review(member, product3, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false);
            final var review3_2 = new Review(member, product3, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false);
            final var review3_3 = new Review(member, product3, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false);
            final var reviews = List.of(review1_1, review1_2, review1_3, review1_4, review2_1, review2_2,
                    review3_1, review3_2, review3_3);
            복수_리뷰_저장(reviews);

            // when
            final var response = 카테고리별_상품_목록_조회_요청(categoryId, "reviewCount", "desc", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, (long) products.size(), 0L);
            카테고리별_상품_목록_조회_결과를_검증한다(response,
                    List.of(product1, product3, product2));
        }

        @Test
        void 리뷰수가_서로_같으면_ID_기준_내림차순으로_정렬할_수_있다() {
            // given
            final var 간편식사 = new Category("간편식사", CategoryType.FOOD);
            final var categoryId = 단일_카테고리_저장(간편식사);
            final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 5.0, 간편식사);
            final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 3.0, 간편식사);
            final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 1.0, 간편식사);
            final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", 4.0, 간편식사);
            final var product5 = new Product("삼각김밥5", 2300L, "image.png", "맛있는 삼각김밥5", 4.5, 간편식사);
            final var products = List.of(product1, product2, product3, product4, product5);
            복수_상품_저장(products);

            final var member = new Member("test", "image.png", "1");
            단일_멤버_저장(member);
            final var review1_1 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true);
            final var review1_2 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true);
            final var review1_3 = new Review(member, product1, "review.png", 4L, "이 삼각김밥은 좀 맛있다", true);
            final var review1_4 = new Review(member, product1, "review.png", 5L, "이 삼각김밥은 최고!!", true);
            final var review2_1 = new Review(member, product2, "review.png", 1L, "이 삼각김밥은 맛없다", false);
            final var review2_2 = new Review(member, product2, "review.png", 1L, "이 삼각김밥은 맛없다", false);
            final var review3_1 = new Review(member, product3, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false);
            final var review3_2 = new Review(member, product3, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false);
            final var review3_3 = new Review(member, product3, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false);
            final var reviews = List.of(review1_1, review1_2, review1_3, review1_4, review2_1, review2_2,
                    review3_1, review3_2, review3_3);
            복수_리뷰_저장(reviews);

            // when
            final var response = 카테고리별_상품_목록_조회_요청(categoryId, "reviewCount", "desc", 0);

            // then
            STATUS_CODE를_검증한다(response, 정상_처리);
            페이지를_검증한다(response, (long) products.size(), 0L);
            카테고리별_상품_목록_조회_결과를_검증한다(response,
                    List.of(product1, product3, product2, product5, product4));
        }
    }

    @Test
    void 상품_상세_정보를_조회한다() {
        // given
        final var 간편식사 = new Category("간편식사", CategoryType.FOOD);
        단일_카테고리_저장(간편식사);
        final var product = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 간편식사);
        final var productId = 단일_상품_저장(product);

        final var tag1 = new Tag("1번", TagType.ETC);
        final var tag2 = new Tag("2번", TagType.ETC);
        final var tag3 = new Tag("3번", TagType.ETC);
        final var tagId1 = 단일_태그_저장(tag1);
        final var tagId2 = 단일_태그_저장(tag2);
        final var tagId3 = 단일_태그_저장(tag3);
        final var image = 리뷰_사진_명세_요청();

        final var request1 = new ReviewCreateRequest(4L, List.of(tagId1, tagId2, tagId3), "request1", true);
        final var request2 = new ReviewCreateRequest(4L, List.of(tagId2, tagId3), "request2", true);
        final var request3 = new ReviewCreateRequest(3L, List.of(tagId2), "request3", true);

        final var loginCookie = 로그인_쿠키를_얻는다();
        단일_리뷰_저장(productId, image, request1, loginCookie);
        단일_리뷰_저장(productId, image, request2, loginCookie);
        단일_리뷰_저장(productId, image, request3, loginCookie);

        // when
        final var response = 상품_상세_조회_요청(productId);

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
        상품_상세_정보_조회_결과를_검증한다(response, product, List.of(tag2, tag3, tag1));
    }

    @Test
    void 전체_상품들_중에서_랭킹_TOP3를_조회할_수_있다() {
        // given
        final var 간편식사 = new Category("간편식사", CategoryType.FOOD);
        단일_카테고리_저장(간편식사);
        final var product1 = new Product("삼각김밥1", 1000L, "image.png", "맛있는 삼각김밥1", 3.25, 간편식사);
        final var product2 = new Product("삼각김밥2", 2000L, "image.png", "맛있는 삼각김밥2", 4.0, 간편식사);
        final var product3 = new Product("삼각김밥3", 1500L, "image.png", "맛있는 삼각김밥3", 3.33, 간편식사);
        final var product4 = new Product("삼각김밥4", 1200L, "image.png", "맛있는 삼각김밥4", 0.0, 간편식사);
        복수_상품_저장(List.of(product1, product2, product3, product4));

        final var member = new Member("test", "image.png", "1");
        단일_멤버_저장(member);
        final var review1_1 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true);
        final var review1_2 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 맛있다", true);
        final var review1_3 = new Review(member, product1, "review.png", 4L, "이 삼각김밥은 좀 맛있다", true);
        final var review1_4 = new Review(member, product1, "review.png", 3L, "이 삼각김밥은 최고!!", true);
        final var review2_1 = new Review(member, product2, "review.png", 4L, "이 삼각김밥은 그럭저럭", false);
        final var review2_2 = new Review(member, product2, "review.png", 4L, "이 삼각김밥은 굿", false);
        final var review3_1 = new Review(member, product3, "review.png", 2L, "이 삼각김밥은 좀 맛없다", false);
        final var review3_2 = new Review(member, product3, "review.png", 3L, "이 삼각김밥은 흠", false);
        final var review3_3 = new Review(member, product3, "review.png", 5L, "이 삼각김밥은 굿굿", false);
        final var reviews = List.of(review1_1, review1_2, review1_3, review1_4, review2_1, review2_2,
                review3_1, review3_2, review3_3);
        복수_리뷰_저장(reviews);

        // when
        final var response = 상품_랭킹_조회_요청();

        // then
        STATUS_CODE를_검증한다(response, 정상_처리);
        상품_랭킹_조회_결과를_검증한다(response, List.of(product2, product3, product1));
    }

    private Long 단일_카테고리_저장(final Category category) {
        return categoryRepository.save(category).getId();
    }

    private Long 단일_상품_저장(final Product product) {
        return productRepository.save(product).getId();
    }

    private Long 단일_태그_저장(final Tag tag) {
        return tagRepository.save(tag).getId();
    }

    private Long 단일_멤버_저장(final Member member) {
        return memberRepository.save(member).getId();
    }

    private void 복수_상품_저장(final List<Product> products) {
        productRepository.saveAll(products);
    }

    private void 복수_리뷰_저장(final List<Review> reviews) {
        reviewRepository.saveAll(reviews);
    }

    private void 페이지를_검증한다(final ExtractableResponse<Response> response, Long dataSize, Long page) {
        final var totalPages = (long) Math.ceil((double) dataSize / PAGE_SIZE);
        final var expected = new ProductsInCategoryPageDto(dataSize, totalPages,
                page == 0, page == totalPages - 1, page, (long) PAGE_SIZE);
        final var actual = response.jsonPath().getObject("page", ProductsInCategoryPageDto.class);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    private void 카테고리별_상품_목록_조회_결과를_검증한다(final ExtractableResponse<Response> response, final List<Product> products) {
        final var expected = new ArrayList<>();
        for (final var product : products) {
            expected.add(ProductInCategoryDto.toDto(product, 0L));
        }

        final var actual = response.jsonPath().getList("products", ProductInCategoryDto.class);

        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("reviewCount")
                .isEqualTo(expected);
    }

    private void 상품_상세_정보_조회_결과를_검증한다(final ExtractableResponse<Response> response, final Product product,
                                      final List<Tag> expectedTags) {
        final var expected = ProductResponse.toResponse(product, expectedTags);
        final var actual = response.as(ProductResponse.class);
        assertThat(actual).usingRecursiveComparison().ignoringFields("averageRating").isEqualTo(expected);
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
}
