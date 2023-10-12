package com.funeat.recipe.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.RecipeFixture.레시피_생성;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RecipeTest {

    @Nested
    class calculateRankingScore_성공_테스트 {

        @Test
        void 꿀조합_좋아요_수와_꿀조합_생성_시간으로_해당_꿀조합의_랭킹_점수를_구할_수_있다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var favoriteCount = 4L;
            final var recipe = 레시피_생성(member, favoriteCount, LocalDateTime.now().minusDays(1L));

            final var expected = favoriteCount / Math.pow(2.0, 0.1);

            // when
            final var actual = recipe.calculateRankingScore();

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }
}
