package com.funeat.review.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class SortingReviewRequest {

    @NotNull(message = "정렬 조건을 확인해주세요")
    private String sort;

    @NotNull(message = "마지막으로 조회한 리뷰 ID를 확인해주세요")
    @PositiveOrZero(message = "마지막으로 조회한 ID는 0 이상이어야 합니다. (처음 조회하면 0)")
    private Long lastReviewId;

    public SortingReviewRequest(final String sort, final Long lastReviewId) {
        this.sort = sort;
        this.lastReviewId = lastReviewId;
    }

    public String getSort() {
        return sort;
    }

    public Long getLastReviewId() {
        return lastReviewId;
    }
}
