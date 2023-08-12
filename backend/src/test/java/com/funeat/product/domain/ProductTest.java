package com.funeat.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ProductTest {

    @Nested
    class updateAverageRating_성공_테스트 {

        @Test
        void 평균_평점을_업데이트_할_수_있다() {
            // given
            final var product = new Product("testName", 1000L, "testImage", "testContent", null);
            final var reviewRating = 4L;
            final var reviewCount = 1L;

            // when
            product.updateAverageRating(reviewRating, reviewCount);
            final var actual = product.getAverageRating();

            // then
            assertThat(actual).isEqualTo(4.0);
        }

        @Test
        void 평균_평점을_여러번_업데이트_할_수_있다() {
            // given
            final var product = new Product("testName", 1000L, "testImage", "testContent", null);
            final var reviewRating1 = 4L;
            final var reviewRating2 = 2L;
            final var reviewCount1 = 1L;
            final var reviewCount2 = 2L;

            // when
            product.updateAverageRating(reviewRating1, reviewCount1);
            final var actual1 = product.getAverageRating();

            product.updateAverageRating(reviewRating2, reviewCount2);
            final var actual2 = product.getAverageRating();

            // then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(actual1)
                        .isEqualTo(4.0);
                softAssertions.assertThat(actual2)
                        .isEqualTo(3.0);
            });
        }
    }

    @Nested
    class updateAverageRating_실패_테스트 {

        @Test
        void 리뷰_평점에_null_값이_들어오면_예외가_발생한다() {
            // given
            final var product = new Product("testName", 1000L, "testImage", "testContent", null);
            final var reviewCount = 1L;

            // when
            assertThatThrownBy(() -> product.updateAverageRating(null, reviewCount))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void 리뷰_평점이_0점이라면_예외가_발생해야하는데_관련_로직이_없어_통과하고_있다() {
            // given
            final var product = new Product("testName", 1000L, "testImage", "testContent", null);
            final var reviewRating = 0L;
            final var reviewCount = 1L;

            // when
            product.updateAverageRating(reviewRating, reviewCount);
        }

        @Test
        void 리뷰_개수가_0개라면_예외가_발생해야하는데_calculatedRating값이_infinity가_나와_통과하고_있다() {
            // given
            final var product = new Product("testName", 1000L, "testImage", "testContent", null);
            final var reviewRating = 3L;
            final var reviewCount = 0L;

            // when
            product.updateAverageRating(reviewRating, reviewCount);
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
