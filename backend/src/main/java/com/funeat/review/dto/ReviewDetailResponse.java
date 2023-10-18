package com.funeat.review.dto;

import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.tag.dto.TagDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewDetailResponse {

    private final Long id;
    private final String userName;
    private final String profileImage;
    private final String image;
    private final Long rating;
    private final List<TagDto> tags;
    private final String content;
    private final boolean rebuy;
    private final Long favoriteCount;
    private final LocalDateTime createdAt;
    private final String categoryType;
    private final Long productId;
    private final String productName;

    public ReviewDetailResponse(final Long id, final String userName, final String profileImage, final String image,
                                final Long rating, final List<TagDto> tags, final String content, final boolean rebuy,
                                final Long favoriteCount, final LocalDateTime createdAt, final String categoryType,
                                final Long productId, final String productName) {
        this.id = id;
        this.userName = userName;
        this.profileImage = profileImage;
        this.image = image;
        this.rating = rating;
        this.tags = tags;
        this.content = content;
        this.rebuy = rebuy;
        this.favoriteCount = favoriteCount;
        this.createdAt = createdAt;
        this.categoryType = categoryType;
        this.productId = productId;
        this.productName = productName;
    }

    public static ReviewDetailResponse toResponse(final Review review) {
        return new ReviewDetailResponse(
                review.getId(),
                review.getMember().getNickname(),
                review.getMember().getProfileImage(),
                review.getImage(),
                review.getRating(),
                findTagDtos(review),
                review.getContent(),
                review.getReBuy(),
                review.getFavoriteCount(),
                review.getCreatedAt(),
                review.getProduct().getCategory().getType().getName(),
                review.getProduct().getId(),
                review.getProduct().getName()
        );
    }

    private static List<TagDto> findTagDtos(final Review review) {
        return review.getReviewTags().stream()
                .map(ReviewTag::getTag)
                .map(TagDto::toDto)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getImage() {
        return image;
    }

    public Long getRating() {
        return rating;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public String getContent() {
        return content;
    }

    public boolean isRebuy() {
        return rebuy;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
}
