package com.funeat.recipe.dto;

import java.util.List;

public class RecipeCommentsResponse {

    private final List<RecipeCommentResponse> comments;
    private final Boolean hasNext;
    private final Long totalElements;

    private RecipeCommentsResponse(final List<RecipeCommentResponse> comments, final Boolean hasNext,
                                   final Long totalElements) {
        this.comments = comments;
        this.hasNext = hasNext;
        this.totalElements = totalElements;
    }

    public static RecipeCommentsResponse toResponse(final List<RecipeCommentResponse> comments, final Boolean hasNext,
                                                    final Long totalElements) {
        return new RecipeCommentsResponse(comments, hasNext, totalElements);
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public List<RecipeCommentResponse> getComments() {
        return comments;
    }

    public Long getTotalElements() {
        return totalElements;
    }
}
