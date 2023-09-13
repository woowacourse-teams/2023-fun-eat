package com.funeat.recipe.dto;

import java.util.List;

public class RankingRecipesResponse {

    private final List<RankingRecipeDto> recipes;

    public RankingRecipesResponse(final List<RankingRecipeDto> recipes) {
        this.recipes = recipes;
    }

    public static RankingRecipesResponse toResponse(final List<RankingRecipeDto> recipes) {
        return new RankingRecipesResponse(recipes);
    }

    public List<RankingRecipeDto> getRecipes() {
        return recipes;
    }
}
