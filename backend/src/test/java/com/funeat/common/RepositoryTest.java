package com.funeat.common;

import com.funeat.member.persistence.MemberRepository;
import com.funeat.member.persistence.ProductBookmarkRepository;
import com.funeat.member.persistence.RecipeBookMarkRepository;
import com.funeat.member.persistence.RecipeFavoriteRepository;
import com.funeat.member.persistence.ReviewFavoriteRepository;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRecipeRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.recipe.persistence.RecipeImageRepository;
import com.funeat.recipe.persistence.RecipeRepository;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.persistence.ReviewTagRepository;
import com.funeat.tag.persistence.TagRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(DataCleaner.class)
@ExtendWith(DataClearExtension.class)
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
}
