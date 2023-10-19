package com.funeat.review.dto;

import com.funeat.review.domain.Review;
import java.time.LocalDateTime;

public class RankingReviewDto {

    private final Long reviewId;
    private final Long productId;
    private final String categoryType;
    private final String productName;
    private final String content;
    private final Long rating;
    private final Long favoriteCount;
    private final LocalDateTime createdAt;

    private RankingReviewDto(final Long reviewId, final Long productId, final String categoryType,
                             final String productName, final String content,
                             final Long rating, final Long favoriteCount, final LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.categoryType = categoryType;
        this.productName = productName;
        this.content = content;
        this.rating = rating;
        this.favoriteCount = favoriteCount;
        this.createdAt = createdAt;
    }

    public static RankingReviewDto toDto(final Review review) {
        return new RankingReviewDto(
                review.getId(),
                review.getProduct().getId(),
                review.getProduct().getCategory().getType().getName(),
                review.getProduct().getName(),
                review.getContent(),
                review.getRating(),
                review.getFavoriteCount(),
                review.getCreatedAt());
    }

    public Long getReviewId() {
        return reviewId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getContent() {
        return content;
    }

    public Long getRating() {
        return rating;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
