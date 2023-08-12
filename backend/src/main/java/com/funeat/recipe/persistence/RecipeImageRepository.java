package com.funeat.recipe.persistence;

import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeImageRepository extends JpaRepository<RecipeImage, Long> {

    List<RecipeImage> findByRecipe(final Recipe recipe);
}
