package com.funeat.recipe.persistence;

import com.funeat.member.domain.Member;
import com.funeat.recipe.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Page<Recipe> findRecipesByMember(final Member member, final Pageable pageable);

    Page<Recipe> findAllRecipes(final Pageable pageable);
}
