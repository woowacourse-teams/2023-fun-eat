package com.funeat.product.persistence;

import com.funeat.common.RepositoryTest;
import com.funeat.product.dto.ProductInCategoryDto;
import com.funeat.product.dto.ProductReviewCountDto;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버3_생성;
import static com.funeat.fixture.PageFixture.가격_내림차순;
import static com.funeat.fixture.PageFixture.가격_오름차순;
import static com.funeat.fixture.PageFixture.페이지요청_기본_생성;
import static com.funeat.fixture.PageFixture.페이지요청_생성;
import static com.funeat.fixture.PageFixture.평균_평점_내림차순;
import static com.funeat.fixture.PageFixture.평균_평점_오름차순;
import static com.funeat.fixture.ProductFixture.상품_망고빙수_가격5000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점2점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격4000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격4000원_평점2점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격5000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_애플망고_가격3000원_평점5점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test1_평점1점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test2_평점2점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test4_평점4점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test5_평점5점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test5_평점5점_재구매X_생성;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class ProductRepositoryTest extends RepositoryTest {

    @Nested
    class findByAllCategory_성공_테스트 {

        @Test
        void 카테고리별_상품을_평점이_높은_순으로_정렬한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product3 = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var product4 = 상품_삼각김밥_가격1000원_평점4점_생성(category);
            final var product5 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
            복수_상품_저장(product1, product2, product3, product4, product5);

            final var page = 페이지요청_생성(0, 3, 평균_평점_내림차순);

            final var productInCategoryDto1 = ProductInCategoryDto.toDto(product5, 0L);
            final var productInCategoryDto2 = ProductInCategoryDto.toDto(product4, 0L);
            final var productInCategoryDto3 = ProductInCategoryDto.toDto(product3, 0L);
            final var expected = List.of(productInCategoryDto1, productInCategoryDto2, productInCategoryDto3);

            // when
            final var actual = productRepository.findAllByCategory(category, page).getContent();

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 카테고리별_상품을_평점이_낮은_순으로_정렬한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product3 = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var product4 = 상품_삼각김밥_가격1000원_평점4점_생성(category);
            final var product5 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
            복수_상품_저장(product1, product2, product3, product4, product5);

            final var page = 페이지요청_생성(0, 3, 평균_평점_오름차순);

            final var productInCategoryDto1 = ProductInCategoryDto.toDto(product1, 0L);
            final var productInCategoryDto2 = ProductInCategoryDto.toDto(product2, 0L);
            final var productInCategoryDto3 = ProductInCategoryDto.toDto(product3, 0L);
            final var expected = List.of(productInCategoryDto1, productInCategoryDto2, productInCategoryDto3);

            // when
            final var actual = productRepository.findAllByCategory(category, page).getContent();

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 카테고리별_상품을_가격이_높은_순으로_정렬한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            final var product3 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
            final var product4 = 상품_삼각김밥_가격4000원_평점1점_생성(category);
            final var product5 = 상품_삼각김밥_가격5000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3, product4, product5);

            final var page = 페이지요청_생성(0, 3, 가격_내림차순);

            final var productInCategoryDto1 = ProductInCategoryDto.toDto(product5, 0L);
            final var productInCategoryDto2 = ProductInCategoryDto.toDto(product4, 0L);
            final var productInCategoryDto3 = ProductInCategoryDto.toDto(product3, 0L);
            final var expected = List.of(productInCategoryDto1, productInCategoryDto2, productInCategoryDto3);

            // when
            final var actual = productRepository.findAllByCategory(category, page).getContent();

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 카테고리별_상품을_가격이_낮은_순으로_정렬한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            final var product3 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
            final var product4 = 상품_삼각김밥_가격4000원_평점1점_생성(category);
            final var product5 = 상품_삼각김밥_가격5000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3, product4, product5);

            final var page = 페이지요청_생성(0, 3, 가격_오름차순);

            final var productInCategoryDto1 = ProductInCategoryDto.toDto(product1, 0L);
            final var productInCategoryDto2 = ProductInCategoryDto.toDto(product2, 0L);
            final var productInCategoryDto3 = ProductInCategoryDto.toDto(product3, 0L);
            final var expected = List.of(productInCategoryDto1, productInCategoryDto2, productInCategoryDto3);

            // when
            final var actual = productRepository.findAllByCategory(category, page).getContent();

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class findAllByCategoryOrderByReviewCountDesc_성공_테스트 {

        @Test
        void 카테고리별_상품을_리뷰수가_많은_순으로_정렬한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            final var product3 = 상품_삼각김밥_가격3000원_평점1점_생성(category);
            final var product4 = 상품_삼각김밥_가격4000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3, product4);

            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            final var member3 = 멤버_멤버3_생성();
            복수_멤버_저장(member1, member2, member3);

            final var review1_1 = 리뷰_이미지test1_평점1점_재구매X_생성(member1, product1, 0L);
            final var review1_2 = 리뷰_이미지test3_평점3점_재구매O_생성(member2, product1, 0L);
            final var review2_1 = 리뷰_이미지test4_평점4점_재구매O_생성(member3, product2, 0L);
            final var review2_2 = 리뷰_이미지test2_평점2점_재구매X_생성(member1, product2, 0L);
            final var review2_3 = 리뷰_이미지test3_평점3점_재구매O_생성(member2, product2, 0L);
            final var review3_1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product3, 0L);
            복수_리뷰_저장(review1_1, review1_2, review2_1, review2_2, review2_3, review3_1);

            final var page = 페이지요청_기본_생성(0, 3);

            final var productInCategoryDto1 = ProductInCategoryDto.toDto(product2, 3L);
            final var productInCategoryDto2 = ProductInCategoryDto.toDto(product1, 2L);
            final var productInCategoryDto3 = ProductInCategoryDto.toDto(product3, 1L);
            final var expected = List.of(productInCategoryDto1, productInCategoryDto2, productInCategoryDto3);

            // when
            final var actual = productRepository.findAllByCategoryOrderByReviewCountDesc(category, page)
                    .getContent();

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class findAllByAverageRatingGreaterThan3_성공_테스트 {

        @Test
        void 평점이_3보다_큰_모든_상품들과_리뷰_수를_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점4점_생성(category);
            final var product3 = 상품_삼각김밥_가격3000원_평점5점_생성(category);
            final var product4 = 상품_삼각김밥_가격4000원_평점2점_생성(category);
            복수_상품_저장(product1, product2, product3, product4);

            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            final var member3 = 멤버_멤버3_생성();
            복수_멤버_저장(member1, member2, member3);

            final var review1_1 = 리뷰_이미지test5_평점5점_재구매X_생성(member1, product1, 0L, LocalDateTime.now().minusDays(2L));
            final var review1_2 = 리뷰_이미지test5_평점5점_재구매X_생성(member2, product1, 0L, LocalDateTime.now().minusDays(3L));
            final var review2_1 = 리뷰_이미지test5_평점5점_재구매X_생성(member3, product2, 0L, LocalDateTime.now().minusDays(10L));
            final var review2_2 = 리뷰_이미지test5_평점5점_재구매X_생성(member1, product2, 0L, LocalDateTime.now().minusDays(1L));
            final var review2_3 = 리뷰_이미지test5_평점5점_재구매X_생성(member2, product2, 0L, LocalDateTime.now().minusDays(9L));
            final var review3_1 = 리뷰_이미지test5_평점5점_재구매X_생성(member1, product3, 0L, LocalDateTime.now().minusDays(8L));
            복수_리뷰_저장(review1_1, review1_2, review2_1, review2_2, review2_3, review3_1);

            final var productReviewCountDto1 = new ProductReviewCountDto(product2, 3L);
            final var productReviewCountDto2 = new ProductReviewCountDto(product3, 1L);
            final var expected = List.of(productReviewCountDto1, productReviewCountDto2);

            // when
            final var startDateTime = LocalDateTime.now().minusWeeks(2L);
            final var endDateTime = LocalDateTime.now();
            final var actual = productRepository.findAllByAverageRatingGreaterThan3(startDateTime, endDateTime);

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 기간_안에_리뷰가_존재하는_상품이_없으면_빈_리스트를_반환한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점4점_생성(category);
            복수_상품_저장(product1, product2);

            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            복수_멤버_저장(member1, member2);

            final var review1 = 리뷰_이미지test5_평점5점_재구매X_생성(member1, product1, 0L, LocalDateTime.now().minusDays(15L));
            final var review2 = 리뷰_이미지test5_평점5점_재구매X_생성(member2, product2, 0L, LocalDateTime.now().minusWeeks(3L));
            복수_리뷰_저장(review1, review2);

            final var expected = Collections.emptyList();

            // when
            final var startDateTime = LocalDateTime.now().minusWeeks(2L);
            final var endDateTime = LocalDateTime.now();
            final var actual = productRepository.findAllByAverageRatingGreaterThan3(startDateTime, endDateTime);

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class findAllByNameContaining_성공_테스트 {

        @Test
        void 상품명에_검색어가_포함된_상품들을_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_애플망고_가격3000원_평점5점_생성(category);
            final var product2 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            복수_상품_저장(product1, product2);

            final var page = 페이지요청_기본_생성(0, 10);

            final var expected = List.of(product2, product1);

            // when
            final var actual = productRepository.findAllByNameContaining("망고", page).getContent();

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class findAllWithReviewCountByNameContaining_성공_테스트 {

        @Test
        void 상품명에_검색어가_포함된_상품들과_리뷰_수를_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_애플망고_가격3000원_평점5점_생성(category);
            final var product2 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            복수_상품_저장(product1, product2);

            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            복수_멤버_저장(member1, member2);

            final var review1_1 = 리뷰_이미지test1_평점1점_재구매X_생성(member1, product1, 0L);
            final var review1_2 = 리뷰_이미지test5_평점5점_재구매O_생성(member2, product1, 0L);
            final var review2_1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product2, 0L);
            복수_리뷰_저장(review1_1, review1_2, review2_1);

            final var page = 페이지요청_기본_생성(0, 10);

            final var expectedDto1 = new ProductReviewCountDto(product1, 2L);
            final var expectedDto2 = new ProductReviewCountDto(product2, 1L);
            final var expected = List.of(expectedDto2, expectedDto1);

            // when
            final var actual = productRepository.findAllWithReviewCountByNameContaining("망고", page).getContent();

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }
}
