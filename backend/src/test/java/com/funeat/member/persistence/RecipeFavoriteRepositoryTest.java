package com.funeat.member.persistence;

import static com.funeat.fixture.CategoryFixture.카테고리_즉석조리_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버3_생성;
import static com.funeat.fixture.ProductFixture.레시피_안에_들어가는_상품_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점1점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점2점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점2점_생성;
import static com.funeat.fixture.RecipeFixture.레시피_생성;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.common.DataCleaner;
import com.funeat.common.DataClearExtension;
import com.funeat.common.RepositoryTest;
import com.funeat.member.domain.favorite.RecipeFavorite;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(DataCleaner.class)
@ExtendWith(DataClearExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RecipeFavoriteRepositoryTest extends RepositoryTest {

    @Nested
    class findByMemberAndRecipe_성공_테스트 {

        @Test
        void 해당_사용자의_해당_레시피에_대한_좋아요_현황을_반환할_수_있다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product3 = 상품_삼각김밥_가격3000원_평점2점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var recipeAuthor = 멤버_멤버1_생성();
            단일_멤버_저장(recipeAuthor);

            final var recipe = 레시피_생성(recipeAuthor);
            단일_레시피_저장(recipe);

            final var productRecipe1 = 레시피_안에_들어가는_상품_생성(product1, recipe);
            final var productRecipe2 = 레시피_안에_들어가는_상품_생성(product2, recipe);
            final var productRecipe3 = 레시피_안에_들어가는_상품_생성(product3, recipe);
            복수_레시피_상품_저장(productRecipe1, productRecipe2, productRecipe3);

            final var realMember = 멤버_멤버2_생성();
            final var fakeMember = 멤버_멤버3_생성();
            복수_멤버_저장(realMember, fakeMember);
            레시피_좋아요_저장(new RecipeFavorite(realMember, recipe, true));

            // when
            final var realMemberActual = recipeFavoriteRepository.findByMemberAndRecipe(realMember, recipe);
            final var fakeMemberActual = recipeFavoriteRepository.findByMemberAndRecipe(fakeMember, recipe);

            // then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(realMemberActual)
                        .isNotEmpty();
                softAssertions.assertThat(fakeMemberActual)
                        .isEmpty();
            });
        }
    }

    @Nested
    class existsByMemberAndRecipeAndFavoriteTrue_성공_테스트 {

        @Test
        void 해당_사용자가_해당_레시피에_좋아요를_눌렀는지_알_수_있다() {
            // given
            final var category = 카테고리_즉석조리_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점1점_생성(category);
            final var product2 = 상품_삼각김밥_가격1000원_평점2점_생성(category);
            final var product3 = 상품_삼각김밥_가격3000원_평점2점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var recipeAuthor = 멤버_멤버1_생성();
            단일_멤버_저장(recipeAuthor);

            final var recipe = 레시피_생성(recipeAuthor);
            단일_레시피_저장(recipe);

            final var productRecipe1 = 레시피_안에_들어가는_상품_생성(product1, recipe);
            final var productRecipe2 = 레시피_안에_들어가는_상품_생성(product2, recipe);
            final var productRecipe3 = 레시피_안에_들어가는_상품_생성(product3, recipe);
            복수_레시피_상품_저장(productRecipe1, productRecipe2, productRecipe3);

            final var realMember = 멤버_멤버2_생성();
            final var fakeMember = 멤버_멤버3_생성();
            복수_멤버_저장(realMember, fakeMember);
            레시피_좋아요_저장(new RecipeFavorite(realMember, recipe, true));

            // when
            final var realMemberActual = recipeFavoriteRepository.existsByMemberAndRecipeAndFavoriteTrue(realMember,
                    recipe);
            final var fakeMemberActual = recipeFavoriteRepository.existsByMemberAndRecipeAndFavoriteTrue(fakeMember,
                    recipe);

            // then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(realMemberActual)
                        .isTrue();
                softAssertions.assertThat(fakeMemberActual)
                        .isFalse();
            });
        }
    }
}
