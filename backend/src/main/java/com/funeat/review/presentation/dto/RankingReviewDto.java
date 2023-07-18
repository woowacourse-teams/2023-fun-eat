package com.funeat.review.presentation.dto;

import com.funeat.review.domain.Review;

public class RankingReviewDto {

    private final Long reviewId;
    private final Long productId;
    private final String productName;
    private final String content;
    private final Double rating;
    private final Long favoriteCount;

    public RankingReviewDto(final Long reviewId,
                            final Long productId,
                            final String productName,
                            final String content,
                            final Double rating,
                            final Long favoriteCount) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.productName = productName;
        this.content = content;
        this.rating = rating;
        this.favoriteCount = favoriteCount;
    }

    public static RankingReviewDto toDto(final Review review) {
        return new RankingReviewDto(
                review.getId(),
                review.getProduct().getId(),
                review.getProduct().getName(),
                review.getContent(),
                review.getRating(),
                review.getFavoriteCount()
        );
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

    public Double getRating() {
        return rating;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }
}
