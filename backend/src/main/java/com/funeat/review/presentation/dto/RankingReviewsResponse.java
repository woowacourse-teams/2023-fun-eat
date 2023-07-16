package com.funeat.review.presentation.dto;

import com.funeat.review.domain.Review;

import java.util.List;

/**
 * {
 * 	    "reviewId": 1,
 * 			"productId": 5,
 * 	    "productName": "구운감자슬림명란마요",
 * 			"content": "할머니가 먹을 거 같은 맛입니다.",
 * 	    "rating": 4.0,
 * 			"favoriteCount": 1256
 *            }
 */
public class RankingReviewsResponse {

    private List<RankingReviewDto> reviews;

    public RankingReviewsResponse(final List<RankingReviewDto> reviews) {
        this.reviews = reviews;
    }

    public static RankingReviewsResponse toResponse(final List<RankingReviewDto> reviews) {
        return new RankingReviewsResponse(reviews);
    }

    public List<RankingReviewDto> getReviews() {
        return reviews;
    }
}
