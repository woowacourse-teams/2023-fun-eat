package com.funeat.review.presentation.dto;

import java.util.List;

public class SortingReviewsResponse {

    private final SortingReviewsPageDto page;
    private final List<SortingReviewDto> reviews;

    public SortingReviewsResponse(final SortingReviewsPageDto page,
                                  final List<SortingReviewDto> reviews) {
        this.page = page;
        this.reviews = reviews;
    }

    public static SortingReviewsResponse toResponse(final SortingReviewsPageDto page,
                                                    final List<SortingReviewDto> reviews) {
        return new SortingReviewsResponse(page, reviews);
    }

    public SortingReviewsPageDto getPage() {
        return page;
    }

    public List<SortingReviewDto> getReviews() {
        return reviews;
    }
}
