package com.funeat.member.dto;

import com.funeat.common.dto.PageDto;
import java.util.List;

public class MemberRecipesResponse {

    private final PageDto page;
    private final List<MemberRecipeDto> recipes;

    private MemberRecipesResponse(final PageDto page, final List<MemberRecipeDto> recipes) {
        this.page = page;
        this.recipes = recipes;
    }

    public static MemberRecipesResponse toResponse(final PageDto page,
                                                   final List<MemberRecipeDto> recipes) {
        return new MemberRecipesResponse(page, recipes);
    }

    public PageDto getPage() {
        return page;
    }

    public List<MemberRecipeDto> getRecipes() {
        return recipes;
    }
}
