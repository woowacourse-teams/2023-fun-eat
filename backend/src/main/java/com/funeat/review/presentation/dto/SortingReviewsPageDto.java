package com.funeat.review.presentation.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.funeat.review.domain.Review;
import org.springframework.data.domain.Page;

public class SortingReviewsPageDto {

    private Long totalDataCount;
    private Long totalPages;
    private boolean isFirstPage;
    private boolean isLastPage;
    private Long requestPage;
    private Long requestSize;

    @JsonCreator
    public SortingReviewsPageDto(final Long totalDataCount,
                                 final Long totalPages,
                                 @JsonProperty(value = "firstPage") final boolean isFirstPage,
                                 @JsonProperty(value = "lastPage") final boolean isLastPage,
                                 final Long requestPage,
                                 final Long requestSize) {
        this.totalDataCount = totalDataCount;
        this.totalPages = totalPages;
        this.isFirstPage = isFirstPage;
        this.isLastPage = isLastPage;
        this.requestPage = requestPage;
        this.requestSize = requestSize;
    }

    public static SortingReviewsPageDto toDto(final Page<Review> page) {
        return new SortingReviewsPageDto(
                page.getTotalElements(),
                Long.valueOf(page.getTotalPages()),
                page.isFirst(),
                page.isLast(),
                Long.valueOf(page.getNumber()),
                Long.valueOf(page.getSize())
        );
    }

    public Long getTotalDataCount() {
        return totalDataCount;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public Long getRequestPage() {
        return requestPage;
    }

    public Long getRequestSize() {
        return requestSize;
    }
}
