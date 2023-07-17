package com.funeat.review.presentation.dto;

public class ReviewFavoriteRequest {

    private final Boolean favorite;
    private final Long memberId;

    public ReviewFavoriteRequest(final Boolean favorite, final Long memberId) {
        this.favorite = favorite;
        this.memberId = memberId;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public Long getMemberId() {
        return memberId;
    }
}
