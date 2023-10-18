package com.funeat.admin.dto;

import java.util.List;

public class AdminReviewSearchResponse {

    private final List<AdminReviewResponse> reviewResponses;
    private final Long totalElements;
    private final Boolean isLastPage;

    public AdminReviewSearchResponse(final List<AdminReviewResponse> reviewResponses, final Long totalElements,
                                     final Boolean isLastPage) {
        this.reviewResponses = reviewResponses;
        this.totalElements = totalElements;
        this.isLastPage = isLastPage;
    }

    public List<AdminReviewResponse> getReviewResponses() {
        return reviewResponses;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public Boolean getLastPage() {
        return isLastPage;
    }
}
