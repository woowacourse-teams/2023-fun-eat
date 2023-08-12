package com.funeat.member.persistence;

import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점3점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test4_평점4점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test5_평점5점_재구매O_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.funeat.common.RepositoryTest;
import com.funeat.member.domain.favorite.ReviewFavorite;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ReviewFavoriteRepositoryTest extends RepositoryTest {

    @Nested
    class findByMemberAndReview_성공_테스트 {

        @Test
        void 멤버와_리뷰로_리뷰_좋아요를_조회할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            단일_상품_저장(product);

            final var review = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 0L);
            단일_리뷰_저장(review);

            final var reviewFavorite = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review, true);
            단일_리뷰_좋아요_저장(reviewFavorite);

            final var expected = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review, true);

            // when
            final var actual = reviewFavoriteRepository.findByMemberAndReview(member, review).get();

            // then
            assertThat(actual).usingRecursiveComparison()
                    .ignoringExpectedNullFields()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class findByMemberAndReview_실패_테스트 {

        @Test
        void 잘못된_멤버로_좋아요를_조회하면_에러가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            단일_상품_저장(product);

            final var review = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 0L);
            단일_리뷰_저장(review);

            final var wrongMember = 멤버_멤버2_생성();
            단일_멤버_저장(wrongMember);

            // when & then
            assertThatThrownBy(() -> reviewFavoriteRepository.findByMemberAndReview(wrongMember, review).get())
                    .isInstanceOf(NoSuchElementException.class);
        }

        @Test
        void 잘못된_리뷰로_좋아요를_조회하면_에러가_발생한다() {
            // given
            final var member = 멤버_멤버1_생성();
            단일_멤버_저장(member);

            final var anotherMember = 멤버_멤버2_생성();
            단일_멤버_저장(anotherMember);

            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            단일_상품_저장(product);

            final var review = 리뷰_이미지test4_평점4점_재구매O_생성(member, product, 0L);
            단일_리뷰_저장(review);

            final var reviewFavorite = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review, true);
            단일_리뷰_좋아요_저장(reviewFavorite);

            final var wrongReview = 리뷰_이미지test5_평점5점_재구매O_생성(member, product, 0L);
            단일_리뷰_저장(wrongReview);

            // when & then
            assertThatThrownBy(() -> reviewFavoriteRepository.findByMemberAndReview(member, wrongReview).get())
                    .isInstanceOf(NoSuchElementException.class);
        }
    }
}
