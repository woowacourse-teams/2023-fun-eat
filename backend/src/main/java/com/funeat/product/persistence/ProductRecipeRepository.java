package com.funeat.product.persistence;

import com.funeat.product.domain.Product;
import com.funeat.product.domain.ProductRecipe;
import com.funeat.recipe.domain.Recipe;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRecipeRepository extends JpaRepository<ProductRecipe, Long> {

    @Query("SELECT pr.product FROM ProductRecipe pr WHERE pr.recipe = :recipe")
    List<Product> findProductByRecipe(final Recipe recipe);
}
