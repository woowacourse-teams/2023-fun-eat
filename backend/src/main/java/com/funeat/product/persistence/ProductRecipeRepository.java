package com.funeat.product.persistence;

import com.funeat.product.domain.ProductRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRecipeRepository extends JpaRepository<ProductRecipe, Long> {
}
