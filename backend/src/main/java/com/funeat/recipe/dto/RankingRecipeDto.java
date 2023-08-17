package com.funeat.recipe.dto;

import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import java.util.List;

public class RankingRecipeDto {

    private final Long id;
    private final String image;
    private final RecipeAuthorDto author;
    private final Long favoriteCount;

    public RankingRecipeDto(final Long id, final String image, final RecipeAuthorDto author, final Long favoriteCount) {
        this.id = id;
        this.image = image;
        this.author = author;
        this.favoriteCount = favoriteCount;
    }

    public static RankingRecipeDto toDto(final Recipe recipe, final List<RecipeImage> images,
                                         final RecipeAuthorDto author) {
        if (images.isEmpty()) {
            return new RankingRecipeDto(recipe.getId(), null, author, recipe.getFavoriteCount());
        }
        return new RankingRecipeDto(recipe.getId(), images.get(0).getImage(), author, recipe.getFavoriteCount());
    }

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public RecipeAuthorDto getAuthor() {
        return author;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }
}
