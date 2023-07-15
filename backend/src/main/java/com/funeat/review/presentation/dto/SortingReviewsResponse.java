package com.funeat.review.presentation.dto;

import java.util.List;

public class SortingReviewsResponse {

    private SortingReviewsPageDto page;
    private List<SortingReviewDto> reviews;

    public SortingReviewsResponse(final SortingReviewsPageDto page,
                                  final List<SortingReviewDto> reviews) {
        this.page = page;
        this.reviews = reviews;
    }

    public SortingReviewsPageDto getPage() {
        return page;
    }

    public List<SortingReviewDto> getReviews() {
        return reviews;
    }
}
