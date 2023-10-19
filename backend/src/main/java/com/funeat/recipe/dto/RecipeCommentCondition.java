package com.funeat.recipe.dto;

public class RecipeCommentCondition {

    private final Long lastId;
    private final Long totalElements;

    public RecipeCommentCondition(final Long lastId, final Long totalElements) {
        this.lastId = lastId;
        this.totalElements = totalElements;
    }

    public Long getLastId() {
        return lastId;
    }

    public Long getTotalElements() {
        return totalElements;
    }
}
