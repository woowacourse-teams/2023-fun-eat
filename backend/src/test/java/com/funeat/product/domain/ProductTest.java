package com.funeat.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ProductTest {

    @Nested
    class updateAverageRatingForInsert_성공_테스트 {

        @Test
        void 평균_평점을_업데이트_할_수_있다() {
            // given
            final var product = new Product("testName", 1000L, "testImage", "testContent", null);
            final var reviewCount = 1L;
            final var reviewRating = 4L;

            // when
            product.updateAverageRatingForInsert(reviewCount, reviewRating);
            final var actual = product.getAverageRating();

            // then
            assertThat(actual).isEqualTo(4.0);
        }

        @Test
        void 평균_평점을_여러번_업데이트_할_수_있다() {
            // given
            final var product = new Product("testName", 1000L, "testImage", "testContent", null);
            final var reviewCount1 = 1L;
            final var reviewCount2 = 2L;
            final var reviewRating1 = 4L;
            final var reviewRating2 = 2L;

            // when
            product.updateAverageRatingForInsert(reviewCount1, reviewRating1);
            final var actual1 = product.getAverageRating();

            product.updateAverageRatingForInsert(reviewCount2, reviewRating2);
            final var actual2 = product.getAverageRating();

            // then
            assertSoftly(soft -> {
                soft.assertThat(actual1)
                        .isEqualTo(4.0);
                soft.assertThat(actual2)
                        .isEqualTo(3.0);
            });
        }
    }

    @Nested
    class updateAverageRatingForDelete_성공_테스트 {

        @Test
        void 리뷰가_하나인_상품의_리뷰를_삭제하면_평균평점은_0점이_된다() {
            // given
            final var product = new Product("testName", 1000L, "testImage", "testContent", 4.0, null, 1L);
            final var reviewRating = 4L;

            // when
            product.updateAverageRatingForDelete(reviewRating);
            final var actual = product.getAverageRating();

            // then
            assertThat(actual).isEqualTo(0.0);
        }

        @Test
        void 리뷰가_여러개인_상품의_리뷰를_삭제하면_평균평점이_갱신된다() {
            // given
            final var product = new Product("testName", 1000L, "testImage", "testContent", 4.0, null, 4L);
            final var reviewRating = 5L;

            // when
            product.updateAverageRatingForDelete(reviewRating);
            final var actual = product.getAverageRating();

            // then
            assertThat(actual).isEqualTo(3.7);
        }
    }

    @Nested
    class calculateRankingScore_성공_테스트 {

        @Test
        void 평균_평점과_리뷰_수로_해당_상품의_랭킹_점수를_구할_수_있다() {
            // given
            final var product = new Product("testName", 1000L, "testImage", "testContent", 4.0, null);
            final var reviewCount = 9L;

            // when
            final var rankingScore = product.calculateRankingScore(reviewCount);

            // then
            assertThat(rankingScore).isEqualTo(3.5);
        }
    }

    @Nested
    class calculateRankingScore_실패_테스트 {

        @Test
        void 리뷰_수가_마이너스_일로_나온다면_무한대로_나와서_예외가_나와야하는데_통과하고_있다() {
            // given
            final var product = new Product("testName", 1000L, "testImage", "testContent", 4.0, null);
            final var reviewCount = -1L;

            // when
            final var rankingScore = product.calculateRankingScore(reviewCount);

            // then
            assertThat(rankingScore).isInfinite();
        }
    }
}
