package com.funeat.review.application;

import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.ImageFixture.이미지_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점2점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰추가요청_재구매O_생성;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static com.funeat.fixture.TagFixture.태그_아침식사_ETC_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import com.funeat.common.EventTest;
import com.funeat.common.ImageUploader;
import com.funeat.tag.domain.Tag;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

class ReviewDeleteEventListenerTest extends EventTest {

    @MockBean
    ImageUploader uploader;

    @Nested
    class 리뷰_삭제_이벤트_발행_유무 {

        @Test
        void 리뷰_작성자가_리뷰_삭제_시도시_리뷰_삭제_이벤트가_발행된다() {
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

            final var request = 리뷰추가요청_재구매O_생성(4L, tagIds);
            reviewService.create(productId, memberId, image, request);

            final var review = reviewRepository.findAll().get(0);
            final var reviewId = review.getId();

            // when
            reviewService.deleteReview(reviewId, memberId);

            // then
            final var count = events.stream(ReviewDeleteEvent.class).count();
            assertThat(count).isEqualTo(1);
        }

        @Test
        void 리뷰_작성자가_아닌_사람이_리뷰_삭제_시도시_리뷰_삭제_이벤트가_발행되지_않는다() {
            // given
            final var author = 멤버_멤버2_생성();
            final var authorId = 단일_멤버_저장(author);
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

            final var request = 리뷰추가요청_재구매O_생성(4L, tagIds);
            reviewService.create(productId, authorId, image, request);

            final var review = reviewRepository.findAll().get(0);
            final var reviewId = review.getId();

            // when
            try {
                reviewService.deleteReview(reviewId, memberId);
            } catch (Exception ignored) {
            }

            // then
            final var count = events.stream(ReviewDeleteEvent.class).count();
            assertThat(count).isEqualTo(0);
        }
    }

    @Nested
    class 이미지_삭제_로직_작동_유무 {

        @Test
        void 리뷰_삭제가_정상적으로_커밋되고_이미지가_존재하면_이미지_삭제_로직이_작동한다() {
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

            final var request = 리뷰추가요청_재구매O_생성(4L, tagIds);
            reviewService.create(productId, memberId, image, request);

            final var review = reviewRepository.findAll().get(0);
            final var reviewId = review.getId();

            // when
            reviewService.deleteReview(reviewId, memberId);

            // then
            verify(uploader, timeout(100).times(1)).delete(any());
        }

        @Test
        void 리뷰_삭제가_정상적으로_커밋되었지만_이미지가_존재하지_않으면_이미지_삭제_로직이_작동하지않는다() {
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
            reviewService.create(productId, memberId, null, request);

            final var review = reviewRepository.findAll().get(0);
            final var reviewId = review.getId();

            // when
            reviewService.deleteReview(reviewId, memberId);

            // then
            verify(uploader, timeout(100).times(0)).delete(any());
        }
    }

    private List<Long> 태그_아이디_변환(final Tag... tags) {
        return Stream.of(tags)
                .map(Tag::getId)
                .collect(Collectors.toList());
    }
}
