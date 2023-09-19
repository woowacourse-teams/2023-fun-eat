package com.funeat.review.persistence;

import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버3_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점2점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점3점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test1_평점1점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test1_평점1점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test4_평점4점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test5_평점5점_재구매O_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.common.RepositoryTest;
import com.funeat.review.dto.SortingReviewDto;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

@SuppressWarnings("NonAsciiCharacters")
class ReviewRepositoryTest extends RepositoryTest {

    @Nested
    class countByProduct_성공_테스트 {

        @Test
        void 상품의_리뷰_수를_반환한다() {
            // given
            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            final var member3 = 멤버_멤버3_생성();
            복수_멤버_저장(member1, member2, member3);

            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2);

            final var review1_1 = 리뷰_이미지test4_평점4점_재구매O_생성(member1, product1, 0L);
            final var review1_2 = 리뷰_이미지test3_평점3점_재구매X_생성(member2, product1, 0L);
            final var review1_3 = 리뷰_이미지test4_평점4점_재구매O_생성(member3, product1, 0L);
            final var review2_1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product2, 0L);
            복수_리뷰_저장(review1_1, review1_2, review1_3, review2_1);

            // when
            final var actual1 = reviewRepository.countByProduct(product1);
            final var actual2 = reviewRepository.countByProduct(product2);

            // then
            assertSoftly(soft -> {
                soft.assertThat(actual1)
                        .isEqualTo(3);
                soft.assertThat(actual2)
                        .isEqualTo(1);
            });
        }
    }

    @Nested
    class findReviewsByProduct_성공_테스트 {

        @Test
        void 특정_상품에_대한_좋아요_기준_내림차순으로_정렬한다() {
            // given
            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            final var member3 = 멤버_멤버3_생성();
            복수_멤버_저장(member1, member2, member3);

            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);

            final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product, 351L);
            final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member2, product, 24L);
            final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product, 130L);
            복수_리뷰_저장(review1, review2, review3);

            final var page = 페이지요청_생성(0, 2, 좋아요수_내림차순);

            final var expected = List.of(review1, review3);

            // when
            final var actual = reviewRepository.findReviewsByProduct(page, product).getContent();

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class findSortingReviewsByFavoriteCountDesc_관련_성공_테스트 {

        @Test
        void 좋아요_기준_내림차순_리뷰_목록의_첫_페이지를_보여준다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 130L);
            final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 24L);
            final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member, product, 351L);
            복수_리뷰_저장(review1, review2, review3);

            final var lastReviewId = 0L;
            final var page = 페이지요청_좋아요_내림차순_생성(0, 2);

            // when
            final var actual = reviewRepository.findSortingReviewsByFavoriteCountDescFirstPage(product, page);

            // then
            assertThat(actual).extracting(SortingReviewDto::getId)
                    .containsExactly(3L, 1L);
        }

        @Test
        void 좋아요_기준_내림차순_리뷰_목록의_2페이지부터_보여준다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 130L);
            final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 24L);
            final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member, product, 351L);
            복수_리뷰_저장(review1, review2, review3);

            final var lastReviewId = 1L;
            final var page = 페이지요청_좋아요_내림차순_생성(0, 2);

            // when
            final var actual = reviewRepository.findSortingReviewsByFavoriteCountDesc(product, lastReviewId, page);

            // then
            assertThat(actual).extracting(SortingReviewDto::getId)
                    .containsExactly(2L);
        }

    }

    @Nested
    class findSortingReviewsByCreatedAtDesc_관련_성공_테스트 {

        @Test
        void 최신순으로_리뷰_목록의_첫_페이지를_보여준다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 130L);
            단일_리뷰_저장(review1);
            final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 24L);
            단일_리뷰_저장(review2);
            final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member, product, 351L);
            단일_리뷰_저장(review3);

            final var page = 페이지요청_생성_시간_내림차순_생성(0, 2);

            // when
            final var actual = reviewRepository.findSortingReviewsByCreatedAtDescFirstPage(product, page);

            // then
            assertThat(actual).extracting(SortingReviewDto::getId)
                    .containsExactly(3L, 2L);
        }

        @Test
        void 최신순으로_리뷰_목록의_2페이지부터_보여준다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);
            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 130L);
            단일_리뷰_저장(review1);
            final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 24L);
            단일_리뷰_저장(review2);
            final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member, product, 351L);
            단일_리뷰_저장(review3);

            final var lastReviewId = 2L;
            final var page = 페이지요청_생성_시간_내림차순_생성(0, 2);

            // when
            final var actual = reviewRepository.findSortingReviewsByCreatedAtDesc(product, lastReviewId, page);

            // then
            assertThat(actual).extracting(SortingReviewDto::getId)
                    .containsExactly(1L);
        }
    }

    @Nested
    class findTop3ByOrderByFavoriteCountDesc_성공_테스트 {

        @Test
        void 전체_리뷰_목록에서_가장_좋아요가_높은_상위_3개의_리뷰를_가져온다() {
            // given
            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            final var member3 = 멤버_멤버3_생성();
            복수_멤버_저장(member1, member2, member3);

            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            복수_상품_저장(product1, product2);

            final var review1_1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product1, 5L);
            final var review1_2 = 리뷰_이미지test4_평점4점_재구매O_생성(member2, product1, 351L);
            final var review1_3 = 리뷰_이미지test3_평점3점_재구매X_생성(member3, product1, 130L);
            final var review2_2 = 리뷰_이미지test5_평점5점_재구매O_생성(member2, product2, 247L);
            final var review3_2 = 리뷰_이미지test1_평점1점_재구매X_생성(member3, product2, 83L);
            복수_리뷰_저장(review1_1, review1_2, review1_3, review2_2, review3_2);

            final var expected = List.of(review1_2, review2_2, review1_3);

            // when
            final var actual = reviewRepository.findTop3ByOrderByFavoriteCountDesc();

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class findPopularReviewWithImage_성공_테스트 {

        @Test
        void 리뷰가_존재하지_않으면_빈_값을_반환하다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var pageable = PageRequest.of(0, 1);

            // when
            final var actual = reviewRepository.findPopularReviewWithImage(productId, pageable);

            // then
            assertThat(actual).isEmpty();
        }

        @Test
        void 리뷰가_존재하면_좋아요_수가_몇개이든_리뷰를_반환하다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var review1 = 리뷰_이미지test1_평점1점_재구매O_생성(member, product, 2L);
            final var review2 = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 1L);
            복수_리뷰_저장(review1, review2);

            final var pageable = PageRequest.of(0, 1);

            // when
            final var actual = reviewRepository.findPopularReviewWithImage(productId, pageable).get(0);

            // then
            assertThat(actual).usingRecursiveComparison().isEqualTo(review1);
        }

        @Test
        void 좋아요_수가_같으면_최신_리뷰를_반환하다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var review1 = 리뷰_이미지test1_평점1점_재구매O_생성(member, product, 0L);
            final var review2 = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 0L);
            복수_리뷰_저장(review1, review2);

            final var pageable = PageRequest.of(0, 1);

            // when
            final var actual = reviewRepository.findPopularReviewWithImage(productId, pageable).get(0);

            // then
            assertThat(actual).usingRecursiveComparison().isEqualTo(review2);
        }
    }

    @Nested
    class findTopByProductOrderByFavoriteCountDescIdDesc_성공_테스트 {

        @Test
        void 좋아요가_가장_많은_리뷰를_반환하다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);

            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var review1 = 리뷰_이미지test1_평점1점_재구매O_생성(member, product, 0L);
            final var review2 = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 4L);
            복수_리뷰_저장(review1, review2);

            // when
            final var actual = reviewRepository.findTopByProductOrderByFavoriteCountDescIdDesc(product);

            // then
            assertThat(actual.get()).isEqualTo(review2);
        }
    }
}
