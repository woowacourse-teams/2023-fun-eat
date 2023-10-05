package com.funeat.admin.dto;

import com.funeat.review.domain.Review;
import java.time.LocalDateTime;

public class AdminReviewResponse {

    private final Long id;
    private final String userName;
    private final String content;
    private final String productName;
    private final LocalDateTime createdAt;

    private AdminReviewResponse(final Long id, final String userName, final String content,
                                final String productName,
                                final LocalDateTime createdAt) {
        this.id = id;
        this.userName = userName;
        this.content = content;
        this.productName = productName;
        this.createdAt = createdAt;
    }

    public static AdminReviewResponse toResponse(final Review review) {
        return new AdminReviewResponse(review.getId(), review.getMember().getNickname(), review.getContent(),
                review.getProduct().getName(), review.getCreatedAt());
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public String getProductName() {
        return productName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
