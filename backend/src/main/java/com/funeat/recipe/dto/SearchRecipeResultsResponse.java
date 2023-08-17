package com.funeat.recipe.dto;

import com.funeat.common.dto.PageDto;
import java.util.List;

public class SearchRecipeResultsResponse {

    private final PageDto page;
    private final List<SearchRecipeResultDto> recipes;

    public SearchRecipeResultsResponse(final PageDto page, final List<SearchRecipeResultDto> recipes) {
        this.page = page;
        this.recipes = recipes;
    }

    public static SearchRecipeResultsResponse toResponse(final PageDto page,
                                                         final List<SearchRecipeResultDto> recipes) {
        return new SearchRecipeResultsResponse(page, recipes);
    }

    public PageDto getPage() {
        return page;
    }

    public List<SearchRecipeResultDto> getRecipes() {
        return recipes;
    }
}
