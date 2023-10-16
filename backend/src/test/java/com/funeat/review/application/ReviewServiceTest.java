package com.funeat.review.application;

import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.ImageFixture.이미지_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버3_생성;
import static com.funeat.fixture.PageFixture.좋아요수_내림차순;
import static com.funeat.fixture.PageFixture.최신순;
import static com.funeat.fixture.PageFixture.페이지요청_기본_생성;
import static com.funeat.fixture.PageFixture.페이지요청_생성;
import static com.funeat.fixture.PageFixture.평점_내림차순;
import static com.funeat.fixture.PageFixture.평점_오름차순;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점2점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점3점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test1_평점1점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test1_평점1점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test2_평점2점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test2_평점2점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test4_평점4점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지없음_평점1점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰정렬요청_존재하지않는정렬_생성;
import static com.funeat.fixture.ReviewFixture.리뷰정렬요청_좋아요수_내림차순_생성;
import static com.funeat.fixture.ReviewFixture.리뷰정렬요청_최신순_생성;
import static com.funeat.fixture.ReviewFixture.리뷰정렬요청_평점_내림차순_생성;
import static com.funeat.fixture.ReviewFixture.리뷰정렬요청_평점_오름차순_생성;
import static com.funeat.fixture.ReviewFixture.리뷰좋아요요청_생성;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매O_생성;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static com.funeat.fixture.TagFixture.태그_아침식사_ETC_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.common.ServiceTest;
import com.funeat.common.dto.PageDto;
import com.funeat.member.dto.MemberReviewDto;
import com.funeat.member.exception.MemberException.MemberNotFoundException;
import com.funeat.product.exception.ProductException.ProductNotFoundException;
import com.funeat.review.domain.Review;
import com.funeat.review.dto.MostFavoriteReviewResponse;
import com.funeat.review.dto.SortingReviewDto;
import com.funeat.review.exception.ReviewException.NotAuthorOfReviewException;
import com.funeat.review.exception.ReviewException.ReviewNotFoundException;
import com.funeat.tag.domain.Tag;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ReviewServiceTest extends ServiceTest {

    @Nested
    class create_성공_테스트 {

        @Test
        void 이미지가_존재하는_리뷰를_추가할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_아침식사_ETC_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);
            final var image = 이미지_생성();
            final var imageFileName = image.getOriginalFilename();

            final var request = 리뷰추가요청_재구매O_생성(4L, tagIds);

            final var expected = new Review(member, product, imageFileName, 4L, "test", true);

            // when
            reviewService.create(productId, memberId, image, request);
            final var actual = reviewRepository.findAll().get(0);

            // then
            assertThat(actual).usingRecursiveComparison()
                    .comparingOnlyFields("member", "product", "image", "rating", "content", "reBuy")
                    .isEqualTo(expected);
        }

        @Test
        void 이미지가_없는_리뷰를_추가할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_아침식사_ETC_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);

            final var request = 리뷰추가요청_재구매O_생성(4L, tagIds);

            final var expected = new Review(member, product, "", 4L, "test", true);

            // when
            reviewService.create(productId, memberId, null, request);
            final var actual = reviewRepository.findAll().get(0);

            // then
            assertThat(actual).usingRecursiveComparison()
                    .comparingOnlyFields("member", "product", "image", "rating", "content", "reBuy")
                    .isEqualTo(expected);
        }
    }

    @Nested
    class create_실패_테스트 {

        @Test
        void 존재하지_않는_멤버로_상품에_리뷰를_추가하면_예외가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var wrongMemberId = 단일_멤버_저장(member) + 1L;

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_아침식사_ETC_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);
            final var image = 이미지_생성();

            final var request = 리뷰추가요청_재구매O_생성(4L, tagIds);

            // when & then
            assertThatThrownBy(() -> reviewService.create(productId, wrongMemberId, image, request))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        void 멤버로_존재하지_않는_상품에_리뷰를_추가하면_예외가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var wrongProductId = 단일_상품_저장(product) + 1L;

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_아침식사_ETC_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);
            final var image = 이미지_생성();

            final var request = 리뷰추가요청_재구매O_생성(4L, tagIds);

            // when & then
            assertThatThrownBy(() -> reviewService.create(wrongProductId, memberId, image, request))
                    .isInstanceOf(ProductNotFoundException.class);
        }
    }

    @Nested
    class likeReview_성공_테스트 {

        @Test
        void 리뷰에_좋아요를_할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_아침식사_ETC_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);
            final var image = 이미지_생성();
            final var reviewCreaterequest = 리뷰추가요청_재구매O_생성(4L, tagIds);
            reviewService.create(productId, memberId, image, reviewCreaterequest);

            final var review = reviewRepository.findAll().get(0);
            final var reviewId = review.getId();

            final var favoriteRequest = 리뷰좋아요요청_생성(true);

            // when
            reviewService.likeReview(reviewId, memberId, favoriteRequest);

            final var actualReview = reviewRepository.findAll().get(0);
            final var actualReviewFavorite = reviewFavoriteRepository.findAll().get(0);

            // then
            assertSoftly(soft -> {
                soft.assertThat(actualReview.getFavoriteCount())
                        .isOne();
                soft.assertThat(actualReviewFavorite.getFavorite())
                        .isTrue();
            });
        }

        @Test
        void 리뷰에_좋아요를_취소_할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_아침식사_ETC_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);
            final var image = 이미지_생성();
            final var reviewCreaterequest = 리뷰추가요청_재구매O_생성(4L, tagIds);
            reviewService.create(productId, memberId, image, reviewCreaterequest);

            final var review = reviewRepository.findAll().get(0);
            final var reviewId = review.getId();

            final var favoriteRequest = 리뷰좋아요요청_생성(true);
            reviewService.likeReview(reviewId, memberId, favoriteRequest);

            // when
            final var cancelFavoriteRequest = 리뷰좋아요요청_생성(false);
            reviewService.likeReview(reviewId, memberId, cancelFavoriteRequest);

            final var actualReview = reviewRepository.findAll().get(0);
            final var actualReviewFavorite = reviewFavoriteRepository.findAll().get(0);

            // then
            assertSoftly(soft -> {
                soft.assertThat(actualReview.getFavoriteCount())
                        .isZero();
                soft.assertThat(actualReviewFavorite.getFavorite())
                        .isFalse();
            });
        }
    }

    @Nested
    class likeReview_실패_테스트 {

        @Test
        void 존재하지_않는_멤버가_리뷰에_좋아요를_하면_예외가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);
            final var wrongMemberId = memberId + 1L;

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_아침식사_ETC_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);
            final var image = 이미지_생성();
            final var reviewCreaterequest = 리뷰추가요청_재구매O_생성(4L, tagIds);
            reviewService.create(productId, memberId, image, reviewCreaterequest);

            final var review = reviewRepository.findAll().get(0);
            final var reviewId = review.getId();

            final var favoriteRequest = 리뷰좋아요요청_생성(true);

            // when
            assertThatThrownBy(() -> reviewService.likeReview(reviewId, wrongMemberId, favoriteRequest))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        void 멤버가_존재하지_않는_리뷰에_좋아요를_하면_예외가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_아침식사_ETC_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);
            final var image = 이미지_생성();
            final var reviewCreaterequest = 리뷰추가요청_재구매O_생성(4L, tagIds);
            reviewService.create(productId, memberId, image, reviewCreaterequest);

            final var review = reviewRepository.findAll().get(0);
            final var reviewId = review.getId();
            final var wrongReviewId = reviewId + 1L;

            final var favoriteRequest = 리뷰좋아요요청_생성(true);

            // when
            assertThatThrownBy(() -> reviewService.likeReview(wrongReviewId, memberId, favoriteRequest))
                    .isInstanceOf(ReviewNotFoundException.class);
        }
    }

    @Nested
    class sortingReviews_성공_테스트 {

        @Test
        void 좋아요_기준으로_내림차순_정렬을_할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);
            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 351L);
            final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 24L);
            final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member, product, 130L);
            복수_리뷰_저장(review1, review2, review3);

            final var request = 리뷰정렬요청_좋아요수_내림차순_생성(0L);

            final var expected = List.of(review1.getId(), review3.getId(), review2.getId());

            // when
            final var actual = reviewService.sortingReviews(productId, memberId, request).getReviews();

            // then
            assertThat(actual).extracting(SortingReviewDto::getId)
                    .containsExactlyElementsOf(expected);
        }

        @Test
        void 최신순으로_정렬을_할_수_있다() throws InterruptedException {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 351L);
            Thread.sleep(100);
            final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 24L);
            Thread.sleep(100);
            final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member, product, 130L);
            복수_리뷰_저장(review1, review2, review3);

            final var request = 리뷰정렬요청_최신순_생성(3L);

            final var expected = List.of(review2.getId(), review1.getId());

            // when
            final var actual = reviewService.sortingReviews(productId, memberId, request).getReviews();

            // then
            assertThat(actual).extracting(SortingReviewDto::getId)
                    .containsExactlyElementsOf(expected);
        }

        @Test
        void 평점_기준으로_오름차순_정렬을_할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var review1 = 리뷰_이미지test2_평점2점_재구매O_생성(member, product, 351L);
            final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 24L);
            final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member, product, 130L);
            복수_리뷰_저장(review1, review2, review3);

            final var request = 리뷰정렬요청_평점_오름차순_생성(0L);

            final var expected = List.of(review1.getId(), review3.getId(), review2.getId());

            // when
            final var actual = reviewService.sortingReviews(productId, memberId, request).getReviews();

            // then
            assertThat(actual).extracting(SortingReviewDto::getId)
                    .containsExactlyElementsOf(expected);
        }

        @Test
        void 평점_기준으로_내림차순_정렬을_할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var review1 = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 5L);
            final var review2 = 리뷰_이미지test2_평점2점_재구매O_생성(member, product, 24L);
            final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member, product, 13L);
            복수_리뷰_저장(review1, review2, review3);

            final var request = 리뷰정렬요청_평점_내림차순_생성(1L);

            final var expected = List.of(review3.getId(), review2.getId());

            // when
            final var actual = reviewService.sortingReviews(productId, memberId, request).getReviews();

            // then
            assertThat(actual).extracting(SortingReviewDto::getId)
                    .containsExactlyElementsOf(expected);
        }
    }

    @Nested
    class sortingReviews_실패_테스트 {

        @Test
        void 존재하지_않는_멤버가_상품에_있는_리뷰들을_정렬하면_예외가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var wrongMemberId = 단일_멤버_저장(member) + 3L;

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 351L);
            final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 24L);
            final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member, product, 130L);
            복수_리뷰_저장(review1, review2, review3);

            final var request = 리뷰정렬요청_평점_내림차순_생성(1L);

            // when & then
            assertThatThrownBy(() -> reviewService.sortingReviews(productId, wrongMemberId, request))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        void 멤버가_존재하지_않는_상품에_있는_리뷰들을_정렬하면_예외가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var wrongProductId = 단일_상품_저장(product) + 1L;

            final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 351L);
            final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 24L);
            final var review3 = 리뷰_이미지test3_평점3점_재구매X_생성(member, product, 130L);
            복수_리뷰_저장(review1, review2, review3);

            final var request = 리뷰정렬요청_평점_내림차순_생성(1L);

            // when & then
            assertThatThrownBy(() -> reviewService.sortingReviews(wrongProductId, memberId, request))
                    .isInstanceOf(ProductNotFoundException.class);
        }
    }

    @Nested
    class findReviewByMember_성공_테스트 {

        @Test
        void 사용자가_작성한_리뷰를_조회한다() {
            // given
            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            복수_멤버_저장(member1, member2);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점5점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점3점_생성(category);
            final var product3 = 상품_삼각김밥_가격2000원_평점1점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var review1_1 = 리뷰_이미지test2_평점2점_재구매X_생성(member1, product3, 0L);

            final var review2_1 = 리뷰_이미지test1_평점1점_재구매X_생성(member1, product2, 0L);
            final var review2_2 = 리뷰_이미지test1_평점1점_재구매X_생성(member2, product2, 0L);

            final var review3_1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product1, 0L);
            final var review3_2 = 리뷰_이미지test3_평점3점_재구매O_생성(member2, product1, 0L);
            복수_리뷰_저장(review1_1, review2_1, review2_2, review3_1, review3_2);

            // when
            final var page = 페이지요청_생성(0, 10, 최신순);
            final var member1Id = member1.getId();
            final var result = reviewService.findReviewByMember(member1Id, page);

            // then
            final var expectedReviews = List.of(review3_1, review2_1, review1_1);
            final var expectedReviewDtos = expectedReviews.stream()
                    .map(MemberReviewDto::toDto)
                    .collect(Collectors.toList());
            final var expectedPage = new PageDto(3L, 1L, true, true, 0L, 10L);

            assertThat(result.getReviews()).usingRecursiveComparison()
                    .isEqualTo(expectedReviewDtos);
            assertThat(result.getPage()).usingRecursiveComparison()
                    .isEqualTo(expectedPage);
        }
    }

    @Nested
    class findReviewByMember_실패_테스트 {

        @Test
        void 존재하지_않은_사용자가_작성한_리뷰를_조회할때_예외가_발생한다() {
            // given
            final var notExistMemberId = 999999L;
            final var page = 페이지요청_생성(0, 10, 최신순);

            // when & then
            assertThatThrownBy(() -> reviewService.findReviewByMember(notExistMemberId, page))
                    .isInstanceOf(MemberNotFoundException.class);
        }
    }

    @Nested
    class updateProductImage_성공_테스트 {

        @Test
        void 처음_리뷰가_등록되면_해당_리뷰의_이미지로_상품_이미지가_변경된다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);

            final var review = 리뷰_이미지test1_평점1점_재구매O_생성(member, product, 0L);
            final var reviewId = 단일_리뷰_저장(review);

            final var expected = review.getImage();

            // when
            reviewService.updateProductImage(product.getId());
            final var actual = product.getImage();

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 가장_많은_좋아요를_받은_리뷰가_바뀌면_해당_리뷰의_이미지로_상품_이미지가_변경된다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);

            final var firstReview = 리뷰_이미지test1_평점1점_재구매O_생성(member, product, 1L);
            final var firstReviewId = 단일_리뷰_저장(firstReview);
            reviewService.updateProductImage(firstReviewId);

            final var secondReview = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 2L);
            final var secondReviewId = 단일_리뷰_저장(secondReview);

            final var expected = secondReview.getImage();

            // when
            reviewService.updateProductImage(product.getId());
            final var actual = product.getImage();

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 리뷰에_좋아요를_했지만_가장_많은_좋아요인_리뷰가_바뀌지_않으면_상품_이미지는_변경되지_않는다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);

            final var firstReview = 리뷰_이미지test1_평점1점_재구매O_생성(member, product, 100L);
            final var firstReviewId = 단일_리뷰_저장(firstReview);
            reviewService.updateProductImage(firstReviewId);

            final var secondReview = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 0L);
            final var secondReviewId = 단일_리뷰_저장(secondReview);

            final var expected = firstReview.getImage();

            // when
            reviewService.updateProductImage(product.getId());
            final var actual = product.getImage();

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 리뷰에_좋아요를_했는데_가장_많은_좋아요가_된_리뷰가_여러개면_최근에_가장_많은_좋아요_리뷰_이미지로_바뀐다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);

            final var firstReview = 리뷰_이미지test1_평점1점_재구매O_생성(member, product, 0L);
            final var firstReviewId = 단일_리뷰_저장(firstReview);
            reviewService.updateProductImage(firstReviewId);

            final var secondReview = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 0L);
            final var secondReviewId = 단일_리뷰_저장(secondReview);

            final var expected = secondReview.getImage();

            // when
            reviewService.updateProductImage(product.getId());
            final var actual = product.getImage();

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 가장_많은_좋아요를_받은_리뷰들에_이미지가_없으면_이미지가_존재하는_좋아요를_많이_받은_리뷰_이미지로_바뀐다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);

            final var firstReview = 리뷰_이미지없음_평점1점_재구매O_생성(member, product, 3L);
            final var firstReviewId = 단일_리뷰_저장(firstReview);
            reviewService.updateProductImage(product.getId());

            final var secondReview = 리뷰_이미지없음_평점1점_재구매O_생성(member, product, 2L);
            final var secondReviewId = 단일_리뷰_저장(secondReview);
            reviewService.updateProductImage(product.getId());

            final var thirdReview = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 1L);
            final var thirdReviewId = 단일_리뷰_저장(thirdReview);

            final var expected = thirdReview.getImage();

            // when
            reviewService.updateProductImage(product.getId());
            final var actual = product.getImage();

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 이미지가_존재하지_않는_리뷰들만_있으면_상품_이미지는_바뀌지_않는다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);

            final var firstReview = 리뷰_이미지없음_평점1점_재구매O_생성(member, product, 3L);
            final var firstReviewId = 단일_리뷰_저장(firstReview);
            reviewService.updateProductImage(product.getId());

            final var secondReview = 리뷰_이미지없음_평점1점_재구매O_생성(member, product, 2L);
            final var secondReviewId = 단일_리뷰_저장(secondReview);

            final var expected = secondReview.getImage();

            // when
            reviewService.updateProductImage(product.getId());
            final var actual = product.getImage();

            // then
            assertThat(actual).isNotEqualTo(expected);
        }
    }

    @Nested
    class updateProductImage_실패_테스트 {

        @Test
        void 존재하지_않는_상품으로_상품_업데이트를_시도하면_예외가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);

            final var wrongProductId = 999L;

            // when & then
            assertThatThrownBy(() -> reviewService.updateProductImage(wrongProductId))
                    .isInstanceOf(ProductNotFoundException.class);
        }
    }

    @Nested
    class deleteReview_성공_테스트 {

        @Test
        void 자신이_작성한_리뷰를_삭제할_수_있다() {
            // given
            final var author = 멤버_멤버1_생성();
            final var authorId = 단일_멤버_저장(author);
            final var member = 멤버_멤버2_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_아침식사_ETC_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);
            final var image = 이미지_생성();
            final var reviewCreateRequest = 리뷰추가요청_재구매O_생성(4L, tagIds);
            reviewService.create(productId, authorId, image, reviewCreateRequest);

            final var review = reviewRepository.findAll().get(0);
            final var reviewId = review.getId();

            final var favoriteRequest = 리뷰좋아요요청_생성(true);
            reviewService.likeReview(reviewId, authorId, favoriteRequest);
            reviewService.likeReview(reviewId, memberId, favoriteRequest);

            // when
            reviewService.deleteReview(reviewId, authorId);

            // then
            final var tags = reviewTagRepository.findAll();
            final var favorites = reviewFavoriteRepository.findAll();
            final var findReview = reviewRepository.findById(reviewId);

            assertSoftly(soft -> {
                soft.assertThat(tags).isEmpty();
                soft.assertThat(favorites).isEmpty();
                soft.assertThat(findReview).isEmpty();
            });
        }
    }

    @Nested
    class deleteReview_실패_테스트 {

        @Test
        void 존재하지_않는_사용자가_리뷰를_삭제하려하면_에러가_발생한다() {
            // given
            final var author = 멤버_멤버1_생성();
            final var authorId = 단일_멤버_저장(author);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_아침식사_ETC_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);
            final var image = 이미지_생성();
            final var reviewCreateRequest = 리뷰추가요청_재구매O_생성(4L, tagIds);
            reviewService.create(productId, authorId, image, reviewCreateRequest);

            final var review = reviewRepository.findAll().get(0);
            final var reviewId = review.getId();

            final var wrongMemberId = 999L;

            // when & then
            assertThatThrownBy(() -> reviewService.deleteReview(reviewId, wrongMemberId))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        void 존재하지_않는_리뷰를_삭제하려하면_에러가_발생한다() {
            // given
            final var author = 멤버_멤버1_생성();
            final var authorId = 단일_멤버_저장(author);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_아침식사_ETC_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);
            final var image = 이미지_생성();
            final var reviewCreateRequest = 리뷰추가요청_재구매O_생성(4L, tagIds);
            reviewService.create(productId, authorId, image, reviewCreateRequest);

            final var wrongReviewId = 999L;

            // when & then
            assertThatThrownBy(() -> reviewService.deleteReview(wrongReviewId, authorId))
                    .isInstanceOf(ReviewNotFoundException.class);
        }

        @Test
        void 자신이_작성하지_않은_리뷰를_삭제하려하면_에러가_발생한다() {
            // given
            final var author = 멤버_멤버1_생성();
            final var authorId = 단일_멤버_저장(author);
            final var member = 멤버_멤버2_생성();
            final var memberId = 단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var tag1 = 태그_맛있어요_TASTE_생성();
            final var tag2 = 태그_아침식사_ETC_생성();
            복수_태그_저장(tag1, tag2);

            final var tagIds = 태그_아이디_변환(tag1, tag2);
            final var image = 이미지_생성();
            final var reviewCreateRequest = 리뷰추가요청_재구매O_생성(4L, tagIds);
            reviewService.create(productId, authorId, image, reviewCreateRequest);

            final var review = reviewRepository.findAll().get(0);
            final var reviewId = review.getId();

            // when & then
            assertThatThrownBy(() -> reviewService.deleteReview(reviewId, memberId))
                    .isInstanceOf(NotAuthorOfReviewException.class);
        }
    }

    @Nested
    class getMostFavoriteReview_성공_테스트 {

        @Test
        void 리뷰가_여러개_존재하면_좋아요를_가장_많이_받은_리뷰를_반환한다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 351L);
            final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 24L);
            복수_리뷰_저장(review1, review2);

            final var expected = MostFavoriteReviewResponse.toResponse(Optional.of(review1));

            // when
            final var actual = reviewService.getMostFavoriteReview(productId);

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 좋아요_수가_같은_리뷰가_여러개_존재하면_가장_최근_작성된_리뷰를_반환한다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var review1 = 리뷰_이미지test3_평점3점_재구매O_생성(member, product, 351L);
            final var review2 = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 351L);
            복수_리뷰_저장(review1, review2);

            final var expected = MostFavoriteReviewResponse.toResponse(Optional.of(review2));

            // when
            final var actual = reviewService.getMostFavoriteReview(productId);

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 리뷰가_존재하지_않으면_Optional_empty를_반환한다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var productId = 단일_상품_저장(product);

            final var expected = Optional.empty();

            // when
            final var actual = reviewService.getMostFavoriteReview(productId);

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    class getMostFavoriteReview_실패_테스트 {

        @Test
        void 존재하지_않는_상품에_가장_많은_좋아요를_받은_리뷰를_찾으면_예외가_발생한다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var wrongProductId = 단일_상품_저장(product) + 1L;

            // when & then
            assertThatThrownBy(() -> reviewService.getMostFavoriteReview(wrongProductId))
                    .isInstanceOf(ProductNotFoundException.class);
        }
    }

    private List<Long> 태그_아이디_변환(final Tag... tags) {
        return Stream.of(tags)
                .map(Tag::getId)
                .collect(Collectors.toList());
    }
}
