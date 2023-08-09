package com.funeat.review.presentation.dto;

import java.util.List;

public class ReviewCreateRequest {

    private final Long rating;
    private final List<Long> tagIds;
    private final String content;
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
