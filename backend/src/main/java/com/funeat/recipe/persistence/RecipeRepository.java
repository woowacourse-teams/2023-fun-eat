package com.funeat.recipe.persistence;

import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import com.funeat.recipe.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Page<Recipe> findRecipesByMember(final Member member, final Pageable pageable);

    Page<Recipe> findAll(final Pageable pageable);

    @Query("SELECT r FROM Recipe r LEFT JOIN ProductRecipe pr ON pr.product = :product WHERE pr.recipe.id = r.id")
    Page<Recipe> findRecipesByProduct(final Product product, final Pageable pageable);
}
