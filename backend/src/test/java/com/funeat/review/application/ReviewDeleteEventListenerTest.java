package com.funeat.review.application;

import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점2점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test1_평점1점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test2_평점2점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test4_평점4점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지없음_평점1점_재구매X_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import com.funeat.common.EventTest;
import com.funeat.common.ImageUploader;
import com.funeat.common.exception.CommonException.S3DeleteFailException;
import com.funeat.exception.CommonErrorCode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

@SuppressWarnings("NonAsciiCharacters")
class ReviewDeleteEventListenerTest extends EventTest {

    @MockBean
    private ImageUploader uploader;

    @Nested
    class 리뷰_삭제_이벤트_발행 {

        @Test
        void 리뷰_작성자가_리뷰_삭제_시도시_리뷰_삭제_이벤트가_발행된다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);

            final var review = reviewRepository.save(리뷰_이미지test1_평점1점_재구매O_생성(member, product, 0L));

            // when
            reviewService.deleteReview(review.getId(), member.getId());

            // then
            final var count = events.stream(ReviewDeleteEvent.class).count();
            assertThat(count).isEqualTo(1);
        }

        @Test
        void 리뷰_작성자가_아닌_사람이_리뷰_삭제_시도시_리뷰_삭제_이벤트가_발행되지_않는다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var author = 멤버_멤버2_생성();
            단일_멤버_저장(author);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);

            final var review = reviewRepository.save(리뷰_이미지test2_평점2점_재구매O_생성(author, product, 0L));

            // when
            try {
                reviewService.deleteReview(review.getId(), member.getId());
            } catch (Exception ignored) {
            }

            // then
            final var count = events.stream(ReviewDeleteEvent.class).count();
            assertThat(count).isEqualTo(0);
        }
    }

    @Nested
    class 이미지_삭제_로직_작동 {

        @Test
        void 리뷰_삭제가_정상적으로_커밋되고_이미지가_존재하면_이미지_삭제_로직이_작동한다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);

            final var review = reviewRepository.save(리뷰_이미지test3_평점3점_재구매O_생성(member, product, 0L));

            // when
            reviewService.deleteReview(review.getId(), member.getId());

            // then
            verify(uploader, timeout(1000).times(1)).delete(any());
        }

        @Test
        void 리뷰_삭제가_정상적으로_커밋되었지만_이미지가_존재하지_않으면_이미지_삭제_로직이_작동하지않는다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);

            final var review = reviewRepository.save(리뷰_이미지없음_평점1점_재구매X_생성(member, product, 0L));

            // when
            reviewService.deleteReview(review.getId(), member.getId());

            // then
            verify(uploader, timeout(1000).times(0)).delete(any());
        }

        @Test
        void 이미지_삭제_로직이_실패해도_메인로직까지_롤백되어서는_안된다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            단일_상품_저장(product);

            final var review = reviewRepository.save(리뷰_이미지test4_평점4점_재구매O_생성(member, product, 0L));

            doThrow(new S3DeleteFailException(CommonErrorCode.UNKNOWN_SERVER_ERROR_CODE))
                    .when(uploader)
                    .delete(any());

            // when
            reviewService.deleteReview(review.getId(), member.getId());

            // then
            assertThat(reviewRepository.findById(review.getId())).isEmpty();
        }
    }
}
