package com.funeat.product.domain.favorite;

import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점_1점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test1_평점1점_재구매O_생성;
import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.member.domain.favorite.ReviewFavorite;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class ReviewFavoriteTest {

    @Nested
    class updateChecked_테스트 {

        @Nested
        class 성공_테스트 {

            @Test
            void 좋아요가_있는_상태에서_좋아요_취소한다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                final var product = 상품_삼각김밥_가격1000원_평점_1점_생성(category);

                final var member = 멤버_멤버1_생성();
                final var review = 리뷰_이미지test1_평점1점_재구매O_생성(member, product, 0L);
                final var reviewFavorite = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review, true);

                // when
                reviewFavorite.updateChecked(false);
                final var actual = reviewFavorite.getFavorite();

                // then
                assertThat(actual).isFalse();
            }

            @Test
            void 좋아요가_없는_상태에서_좋아요를_적용한다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                final var product = 상품_삼각김밥_가격1000원_평점_1점_생성(category);

                final var member = 멤버_멤버1_생성();
                final var review = 리뷰_이미지test1_평점1점_재구매O_생성(member, product, 0L);
                final var reviewFavorite = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review, false);

                // when
                reviewFavorite.updateChecked(true);
                final var actual = reviewFavorite.getFavorite();

                // then
                assertThat(actual).isTrue();
            }
        }

        @Nested
        class 실패_테스트 {

            @Test
            void 좋아요가_있는_상태에서_좋아요를_적용하면_예외가_발생해야하는데_통과하고_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                final var product = 상품_삼각김밥_가격1000원_평점_1점_생성(category);

                final var member = 멤버_멤버1_생성();
                final var review = 리뷰_이미지test1_평점1점_재구매O_생성(member, product, 0L);
                final var reviewFavorite = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review, true);

                // when
                reviewFavorite.updateChecked(true);
                final var actual = reviewFavorite.getFavorite();

                // then
                assertThat(actual).isTrue();
            }

            @Test
            void 좋아요가_없는_상태에서_좋아요를_취소하면_예외가_발생해야하는데_통과하고_있다() {
                // given
                final var category = 카테고리_즉석조리_생성();
                final var product = 상품_삼각김밥_가격1000원_평점_1점_생성(category);

                final var member = 멤버_멤버1_생성();
                final var review = 리뷰_이미지test1_평점1점_재구매O_생성(member, product, 0L);
                final var reviewFavorite = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review, false);

                // when
                reviewFavorite.updateChecked(false);
                final var actual = reviewFavorite.getFavorite();

                // then
                assertThat(actual).isFalse();
            }
        }
    }
}
