package com.funeat.common;

import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.member.persistence.ProductBookmarkRepository;
import com.funeat.member.persistence.RecipeBookMarkRepository;
import com.funeat.member.persistence.RecipeFavoriteRepository;
import com.funeat.member.persistence.ReviewFavoriteRepository;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import com.funeat.product.domain.ProductRecipe;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRecipeRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import com.funeat.recipe.persistence.RecipeImageRepository;
import com.funeat.recipe.persistence.RecipeRepository;
import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.persistence.ReviewTagRepository;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(DataCleaner.class)
@ExtendWith(DataClearExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public abstract class RepositoryTest {

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected ProductBookmarkRepository productBookmarkRepository;

    @Autowired
    protected RecipeBookMarkRepository recipeBookMarkRepository;

    @Autowired
    protected RecipeFavoriteRepository recipeFavoriteRepository;

    @Autowired
    protected ReviewFavoriteRepository reviewFavoriteRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected ProductRecipeRepository productRecipeRepository;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected RecipeImageRepository recipeImageRepository;

    @Autowired
    protected RecipeRepository recipeRepository;

    @Autowired
    protected ReviewRepository reviewRepository;

    @Autowired
    protected ReviewTagRepository reviewTagRepository;

    @Autowired
    protected TagRepository tagRepository;

    protected Long 단일_상품_저장(final Product product) {
        return productRepository.save(product).getId();
    }

    protected void 복수_상품_저장(final Product... productsToSave) {
        final var products = List.of(productsToSave);

        productRepository.saveAll(products);
    }

    protected Long 단일_카테고리_저장(final Category category) {
        return categoryRepository.save(category).getId();
    }

    protected void 복수_카테고리_저장(final Category... categoriesToSave) {
        final var categories = List.of(categoriesToSave);

        categoryRepository.saveAll(categories);
    }

    protected Long 단일_멤버_저장(final Member member) {
        return memberRepository.save(member).getId();
    }

    protected void 복수_멤버_저장(final Member... membersToSave) {
        final var members = List.of(membersToSave);

        memberRepository.saveAll(members);
    }

    protected Long 단일_리뷰_저장(final Review review) {
        return reviewRepository.save(review).getId();
    }

    protected void 복수_리뷰_저장(final Review... reviewsToSave) {
        final var reviews = List.of(reviewsToSave);

        reviewRepository.saveAll(reviews);
    }

    protected Long 단일_태그_저장(final Tag tag) {
        return tagRepository.save(tag).getId();
    }

    protected void 복수_태그_저장(final Tag... tagsToSave) {
        final var tags = List.of(tagsToSave);

        tagRepository.saveAll(tags);
    }

    protected Long 단일_리뷰_태그_저장(final ReviewTag reviewTag) {
        return reviewTagRepository.save(reviewTag).getId();
    }

    protected void 복수_리뷰_태그_저장(final ReviewTag... reviewTagsToSave) {
        final var reviewTags = List.of(reviewTagsToSave);

        reviewTagRepository.saveAll(reviewTags);
    }

    protected Long 단일_리뷰_좋아요_저장(final ReviewFavorite reviewFavorite) {
        return reviewFavoriteRepository.save(reviewFavorite).getId();
    }

    protected void 복수_리뷰_좋아요_저장(final ReviewFavorite... reviewFavoritesToSave) {
        final var reviewFavorites = List.of(reviewFavoritesToSave);

        reviewFavoriteRepository.saveAll(reviewFavorites);
    }

    protected Long 단일_레시피_저장(final Recipe recipe) {
        return recipeRepository.save(recipe).getId();
    }

    protected void 복수_레시피_저장(final Recipe... recipeToSave) {
        final var recipes = List.of(recipeToSave);

        recipeRepository.saveAll(recipes);
    }

    protected void 복수_레시피_이미지_저장(final RecipeImage... recipeImageToSave) {
        final var images = List.of(recipeImageToSave);

        recipeImageRepository.saveAll(images);
    }

    protected void 복수_레시피_상품_저장(final ProductRecipe... productRecipeToSave) {
        final var productRecipes = List.of(productRecipeToSave);

        productRecipeRepository.saveAll(productRecipes);
    }
}
