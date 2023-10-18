package com.funeat.review.domain;

import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점1점_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test5_평점5점_재구매X_생성;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReviewTest {

    @Nested
    class calculateRankingScore_성공_테스트 {

        @Test
        void 리뷰_좋아요_수와_리뷰_생성_시간으로_해당_리뷰의_랭킹_점수를_구할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var category = 카테고리_간편식사_생성();
            final var product = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var favoriteCount = 4L;
            final var review = 리뷰_이미지test5_평점5점_재구매X_생성(member, product, favoriteCount, LocalDateTime.now().minusDays(1L));

            final var expected = favoriteCount / Math.pow(2.0, 0.5);

            // when
            final var actual = review.calculateRankingScore();

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }
}
