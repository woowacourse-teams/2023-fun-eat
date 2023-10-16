package com.funeat.recipe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RecipeCommentCreateRequest {

    @NotBlank(message = "꿀조합 댓글을 확인해 주세요")
    @Size(max = 200, message = "꿀조합 댓글은 최대 200자까지 입력 가능합니다")
    private final String comment;

    public RecipeCommentCreateRequest(@JsonProperty("comment") final String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
}
