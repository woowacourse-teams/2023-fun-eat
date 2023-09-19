package com.funeat.review.dto;

import com.funeat.common.dto.PageDto;
import java.util.List;

public class SortingReviewsResponse {

    private final List<SortingReviewDto> reviews;
    private final Boolean hasNextReview;

    public SortingReviewsResponse(final List<SortingReviewDto> reviews, final Boolean hasNextReview) {
        this.reviews = reviews;
        this.hasNextReview = hasNextReview;
    }

    public static SortingReviewsResponse toResponse(final List<SortingReviewDto> reviews, final Boolean hasNextReview) {
        return new SortingReviewsResponse(reviews, hasNextReview);
    }

    public List<SortingReviewDto> getReviews() {
        return reviews;
    }

    public Boolean getHasNextReview() {
        return hasNextReview;
    }
}
