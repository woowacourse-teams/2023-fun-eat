package com.funeat.review.presentation.dto;

import com.funeat.common.dto.PageDto;
import java.util.List;

public class SortingReviewsResponse {

    private final PageDto page;
    private final List<SortingReviewDto> reviews;

    public SortingReviewsResponse(final PageDto page, final List<SortingReviewDto> reviews) {
        this.page = page;
        this.reviews = reviews;
    }

    public static SortingReviewsResponse toResponse(final PageDto page, final List<SortingReviewDto> reviews) {
        return new SortingReviewsResponse(page, reviews);
    }

    public PageDto getPage() {
        return page;
    }

    public List<SortingReviewDto> getReviews() {
        return reviews;
    }
}
