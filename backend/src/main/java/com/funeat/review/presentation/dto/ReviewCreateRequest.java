package com.funeat.review.presentation.dto;

import java.util.List;

public class ReviewCreateRequest {

    private final Long rating;
    private final List<Long> tagIds;
    private final String content;
    private final Boolean reBuy;

    public ReviewCreateRequest(final Long rating, final List<Long> tagIds, final String content, final Boolean reBuy) {
        this.rating = rating;
        this.tagIds = tagIds;
        this.content = content;
        this.reBuy = reBuy;
    }

    public Long getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public Boolean getReBuy() {
        return reBuy;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }
}
