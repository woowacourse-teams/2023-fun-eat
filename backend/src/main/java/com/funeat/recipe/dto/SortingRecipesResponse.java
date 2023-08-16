package com.funeat.recipe.dto;

import com.funeat.common.dto.PageDto;
import java.util.List;

public class SortingRecipesResponse {

    private final PageDto page;
    private final List<RecipeDto> recipes;

    public SortingRecipesResponse(final PageDto page, final List<RecipeDto> recipes) {
        this.page = page;
        this.recipes = recipes;
    }

    public static SortingRecipesResponse toResponse(final PageDto page, final List<RecipeDto> recipes) {
        return new SortingRecipesResponse(page, recipes);
    }

    public PageDto getPage() {
        return page;
    }

    public List<RecipeDto> getRecipes() {
        return recipes;
    }
}
