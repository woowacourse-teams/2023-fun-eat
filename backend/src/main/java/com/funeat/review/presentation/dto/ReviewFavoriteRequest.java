package com.funeat.review.presentation.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewFavoriteRequest {

    private final Boolean favorite;

    @JsonCreator
    public ReviewFavoriteRequest(@JsonProperty("favorite") final Boolean favorite) {
        this.favorite = favorite;
    }

    public Boolean getFavorite() {
        return favorite;
    }
}
