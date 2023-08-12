package com.funeat.member.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.common.DataCleaner;
import com.funeat.common.DataClearExtension;
import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.RecipeFavorite;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;
import com.funeat.product.domain.Product;
import com.funeat.product.domain.ProductRecipe;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRecipeRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.persistence.RecipeRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(DataCleaner.class)
@ExtendWith(DataClearExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RecipeFavoriteRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ProductRecipeRepository productRecipeRepository;

    @Autowired
    private RecipeFavoriteRepository recipeFavoriteRepository;

    @Test
    void 사용자의_레시피에_대한_좋아요_현황을_알_수_있다() {
        // given
        final var category = 카테고리_추가_요청(new Category("간편식사", CategoryType.FOOD));
        final var product1 = 상품_추가_요청(new Product("불닭볶음면", 1000L, "image.png", "엄청 매운 불닭", category));
        final var product2 = 상품_추가_요청(new Product("참치 삼김", 2000L, "image.png", "담백한 참치마요 삼김", category));
        final var product3 = 상품_추가_요청(new Product("스트링 치즈", 1500L, "image.png", "고소한 치즈", category));
        final var recipeAuthor = 멤버_추가_요청(new Member("author", "image.png", "1"));
        final var products = List.of(product1, product2, product3);

        final var recipe = 레시피_추가_요청(new Recipe("레시피1", "밥 넣고 밥 넣자", recipeAuthor));
        products.forEach(it -> 레시피에_사용된_상품_추가_요청(new ProductRecipe(it, recipe)));

        // when
        final var realMember = 멤버_추가_요청(new Member("real", "image.png", "2"));
        final var fakeMember = 멤버_추가_요청(new Member("fake", "image.png", "3"));
        레시피_좋아요_요청(new RecipeFavorite(realMember, recipe, true));

        final var realMemberActual = recipeFavoriteRepository.existsByMemberAndRecipeAndFavoriteTrue(realMember, recipe);
        final var fakeMemberActual = recipeFavoriteRepository.existsByMemberAndRecipeAndFavoriteTrue(fakeMember, recipe);

        // then
        assertThat(realMemberActual).isTrue();
        assertThat(fakeMemberActual).isFalse();
    }

    private Category 카테고리_추가_요청(final Category category) {
        return categoryRepository.save(category);
    }

    private Product 상품_추가_요청(final Product product) {
        return productRepository.save(product);
    }

    private Member 멤버_추가_요청(final Member member) {
        return memberRepository.save(member);
    }

    private Recipe 레시피_추가_요청(final Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    private ProductRecipe 레시피에_사용된_상품_추가_요청(final ProductRecipe productRecipe) {
        return productRecipeRepository.save(productRecipe);
    }

    private RecipeFavorite 레시피_좋아요_요청(final RecipeFavorite recipeFavorite) {
        return recipeFavoriteRepository.save(recipeFavorite);
    }
}
