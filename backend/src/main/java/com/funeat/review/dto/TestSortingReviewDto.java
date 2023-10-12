package com.funeat.review.dto;

import java.time.LocalDateTime;

public class TestSortingReviewDto {

    private Long id;
    private String userName;
    private String profileImage;
    private String image;
    private Long rating;
    private String content;
    private Boolean rebuy;
    private Long favoriteCount;
    private Boolean favorite;
    private LocalDateTime createdAt;

    public TestSortingReviewDto(final Long id, final String userName, final String profileImage, final String image,
                                final Long rating, final String content,
                                final Boolean rebuy, final Long favoriteCount, final Boolean favorite,
                                final LocalDateTime createdAt) {
        this.id = id;
        this.userName = userName;
        this.profileImage = profileImage;
        this.image = image;
        this.rating = rating;
        this.content = content;
        this.rebuy = rebuy;
        this.favoriteCount = favoriteCount;
        this.favorite = favorite;
        this.createdAt = createdAt;
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

    public String getContent() {
        return content;
    }

    public Boolean getRebuy() {
        return rebuy;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
