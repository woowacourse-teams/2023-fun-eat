package com.funeat.recipe.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public class RecipeFavoriteRequest {

    @NotNull(message = "좋아요를 확인해주세요")
    private final Boolean favorite;

    @JsonCreator
    public RecipeFavoriteRequest(@JsonProperty("favorite") final Boolean favorite) {
        this.favorite = favorite;
    }

    public Boolean getFavorite() {
        return favorite;
    }
}
