package com.funeat.review.presentation.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ReviewCreateRequest {

    @NotNull(message = "평점을 확인해 주세요")
    private final Long rating;

    @NotNull(message = "태그 ID 목록을 확인해 주세요")
    @Size(min = 1, message = "적어도 1개의 태그 ID가 필요합니다")
    private final List<Long> tagIds;

    @NotBlank(message = "리뷰 내용을 확인해 주세요")
    private final String content;

    @NotNull(message = "재구매 여부를 입력해주세요")
    private final Boolean rebuy;

    public ReviewCreateRequest(final Long rating, final List<Long> tagIds, final String content, final Boolean rebuy) {
        this.rating = rating;
        this.tagIds = tagIds;
        this.content = content;
        this.rebuy = rebuy;
    }

    public Long getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public Boolean getRebuy() {
        return rebuy;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }
}
