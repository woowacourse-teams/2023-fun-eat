package com.funeat.review.presentation.dto;

public class ReviewFavoriteRequest {

    private final Boolean favorite;

    public ReviewFavoriteRequest(final Boolean favorite) {
        this.favorite = favorite;
    }

    public Boolean getFavorite() {
        return favorite;
    }
}
