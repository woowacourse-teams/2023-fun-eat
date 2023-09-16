package com.funeat.member.domain.favorite;

import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.RecipeFixture.레시피_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RecipeFavoriteTest {

    @Nested
    class create_성공_테스트 {

        @Test
        void create를_통한_생성시_favorite은_false로_초기화된다() {
            // given
            final var member = 멤버_멤버1_생성();
            final var recipe = 레시피_생성(member);

            // when
            final var actual = RecipeFavorite.create(member, recipe);

            // then
            assertThat(actual.getFavorite()).isFalse();
        }
    }

    @Nested
    class updateFavorite_성공_테스트 {

        @Test
        void 기존_false_신규_true_경우_recipe의_favoriteCount는_1_증가() {
            // given
            final var member = 멤버_멤버1_생성();
            final var recipe = 레시피_생성(member);
            final var recipeFavorite = RecipeFavorite.create(member, recipe);

            // when
            recipeFavorite.updateFavorite(true);

            // then
            assertSoftly(soft -> {
                soft.assertThat(recipeFavorite.getRecipe().getFavoriteCount())
                        .isOne();
                soft.assertThat(recipeFavorite.getFavorite())
                        .isTrue();
            });
        }

        @Test
        void 기존_true_신규_false_경우_recipe의_favoriteCount는_1_감소() {
            // given
            final var member = 멤버_멤버1_생성();
            final var recipe = 레시피_생성(member);

            final var recipeFavorite = RecipeFavorite.create(member, recipe);
            recipeFavorite.updateFavorite(true);

            // when
            recipeFavorite.updateFavorite(false);

            // then
            assertSoftly(soft -> {
                soft.assertThat(recipeFavorite.getRecipe().getFavoriteCount())
                        .isZero();
                soft.assertThat(recipeFavorite.getFavorite())
                        .isFalse();
            });
        }

        @Test
        void 기존_true_신규_true_경우_recipe의_favoriteCount는_유지() {
            // given
            final var member = 멤버_멤버1_생성();
            final var recipe = 레시피_생성(member);

            final var recipeFavorite = RecipeFavorite.create(member, recipe);
            recipeFavorite.updateFavorite(true);

            // when
            recipeFavorite.updateFavorite(true);

            // then
            assertSoftly(soft -> {
                soft.assertThat(recipeFavorite.getRecipe().getFavoriteCount())
                        .isOne();
                soft.assertThat(recipeFavorite.getFavorite())
                        .isTrue();
            });
        }

        @Test
        void 기존_false_신규_false_경우_recipe의_favoriteCount는_유지() {
            // given
            final var member = 멤버_멤버1_생성();
            final var recipe = 레시피_생성(member);

            final var recipeFavorite = RecipeFavorite.create(member, recipe);

            // when
            recipeFavorite.updateFavorite(false);

            // then
            assertSoftly(soft -> {
                soft.assertThat(recipeFavorite.getRecipe().getFavoriteCount())
                        .isZero();
                soft.assertThat(recipeFavorite.getFavorite())
                        .isFalse();
            });
        }
    }
}
