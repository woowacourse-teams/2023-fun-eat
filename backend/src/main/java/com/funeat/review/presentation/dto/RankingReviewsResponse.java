package com.funeat.review.presentation.dto;

import java.util.List;

public class RankingReviewsResponse {

    private List<RankingReviewDto> reviews;

    public RankingReviewsResponse(final List<RankingReviewDto> reviews) {
        this.reviews = reviews;
    }

    public static RankingReviewsResponse toResponse(final List<RankingReviewDto> reviews) {
        return new RankingReviewsResponse(reviews);
    }

    public List<RankingReviewDto> getReviews() {
        return reviews;
    }
}
