package com.funeat.review.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class SortingReviewRequest {

    private static final int START_PAGE = 0;
    private static final int ELEVEN = 11;
    private static final String DELIMITER = ",";
    private static final int ORDER_INDEX = 0;
    private static final int DIRECTION_INDEX = 1;
    private static final String ID = "id";

    @NotNull(message = "정렬 조건을 확인해주세요")
    @Pattern(regexp = "^(createdAt,desc|favoriteCount,desc|rating,desc|rating,asc)$",
            message = "정렬 조건은 'createdAt,desc', 'favoriteCount,desc', 'rating,desc', 'rating,asc' 중 하나만 가능합니다.")
    private String sort;

    @NotNull(message = "마지막으로 조회한 리뷰 ID를 확인해주세요")
    @PositiveOrZero(message = "마지막으로 조회한 ID는 0 이상이어야 합니다. (처음 조회하면 0)")
    private Long lastReviewId;

    public SortingReviewRequest(final String sort, final Long lastReviewId) {
        this.sort = sort;
        this.lastReviewId = lastReviewId;
    }

    public Pageable getPageable() {
        final String[] splitSort = sort.split(DELIMITER);
        final String order = splitSort[ORDER_INDEX];
        final Direction direction = Direction.fromString(splitSort[DIRECTION_INDEX]);

        return PageRequest.of(START_PAGE, ELEVEN, Sort.by(direction, order).and(Sort.by(Direction.ASC, ID)));
    }

    public String getSort() {
        return sort;
    }

    public Long getLastReviewId() {
        return lastReviewId;
    }
}
