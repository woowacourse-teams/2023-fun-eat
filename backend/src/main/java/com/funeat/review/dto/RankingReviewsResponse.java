package com.funeat.review.dto;

import java.util.List;

public class RankingReviewsResponse {

    private final List<RankingReviewDto> reviews;

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
