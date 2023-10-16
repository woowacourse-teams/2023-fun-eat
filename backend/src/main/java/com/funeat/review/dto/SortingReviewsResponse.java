package com.funeat.review.dto;

import java.util.List;

public class SortingReviewsResponse {

    private final List<SortingReviewDto> reviews;
    private final Boolean hasNext;

    public SortingReviewsResponse(final List<SortingReviewDto> reviews, final Boolean hasNext) {
        this.reviews = reviews;
        this.hasNext = hasNext;
    }

    public static SortingReviewsResponse toResponse(final List<SortingReviewDto> reviews, final Boolean hasNextReview) {
        return new SortingReviewsResponse(reviews, hasNextReview);
    }

    public List<SortingReviewDto> getReviews() {
        return reviews;
    }

    public Boolean getHasNext() {
        return hasNext;
    }
}
