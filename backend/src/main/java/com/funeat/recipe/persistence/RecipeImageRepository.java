package com.funeat.recipe.persistence;

import com.funeat.recipe.domain.RecipeImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeImageRepository extends JpaRepository<RecipeImage, Long> {
}
