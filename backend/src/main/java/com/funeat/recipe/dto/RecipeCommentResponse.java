package com.funeat.recipe.dto;

import com.funeat.comment.domain.Comment;
import java.time.LocalDateTime;

public class RecipeCommentResponse {

    private final Long id;
    private final String comment;
    private final LocalDateTime createdAt;
    private final RecipeCommentMemberResponse author;

    private RecipeCommentResponse(final Long id, final String comment, final LocalDateTime createdAt,
                                  final RecipeCommentMemberResponse author) {
        this.id = id;
        this.comment = comment;
        this.createdAt = createdAt;
        this.author = author;
    }

    public static RecipeCommentResponse toResponse(final Comment comment) {
        final RecipeCommentMemberResponse author = RecipeCommentMemberResponse.toResponse(comment.getMember());

        return new RecipeCommentResponse(comment.getId(), comment.getComment(), comment.getCreatedAt(), author);
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public RecipeCommentMemberResponse getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "RecipeCommentResponse{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
