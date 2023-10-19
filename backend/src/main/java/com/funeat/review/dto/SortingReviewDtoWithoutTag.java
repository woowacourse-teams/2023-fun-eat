package com.funeat.review.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class SortingReviewDtoWithoutTag {

    private final Long id;
    private final String userName;
    private final String profileImage;
    private final String image;
    private final Long rating;
    private final String content;
    private final boolean rebuy;
    private final Long favoriteCount;
    private final boolean favorite;
    private final LocalDateTime createdAt;

    public SortingReviewDtoWithoutTag(final Long id, final String userName, final String profileImage,
                                      final String image, final Long rating,
                                      final String content, final boolean rebuy, final Long favoriteCount,
                                      final Boolean favorite,
                                      final LocalDateTime createdAt) {
        final Boolean isFavorite = checkingFavorite(favorite);

        this.id = id;
        this.userName = userName;
        this.profileImage = profileImage;
        this.image = image;
        this.rating = rating;
        this.content = content;
        this.rebuy = rebuy;
        this.favoriteCount = favoriteCount;
        this.favorite = isFavorite;
        this.createdAt = createdAt;
    }

    private static Boolean checkingFavorite(final Boolean favorite) {
        if (Objects.isNull(favorite)) {
            return Boolean.FALSE;
        }
        return favorite;
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

    public boolean getRebuy() {
        return rebuy;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
