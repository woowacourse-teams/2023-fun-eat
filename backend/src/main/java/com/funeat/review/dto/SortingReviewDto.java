package com.funeat.review.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.dto.TagDto;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SortingReviewDto {

    private final Long id;
    private final String userName;
    private final String profileImage;
    private final String image;
    private final Long rating;
    private final List<TagDto> tags;
    private final String content;
    private final Boolean rebuy;
    private final Long favoriteCount;
    private final Boolean favorite;
    private final LocalDateTime createdAt;

    @JsonCreator
    public SortingReviewDto(final Long id, final String userName, final String profileImage, final String image,
                            final Long rating, final List<TagDto> tags,
                            final String content, final Boolean rebuy, final Long favoriteCount, final Boolean favorite,
                            final LocalDateTime createdAt) {
        this.id = id;
        this.userName = userName;
        this.profileImage = profileImage;
        this.image = image;
        this.rating = rating;
        this.tags = tags;
        this.content = content;
        this.rebuy = rebuy;
        this.favoriteCount = favoriteCount;
        this.favorite = favorite;
        this.createdAt = createdAt;
    }

    public static SortingReviewDto toDto(final SortingReviewDtoWithoutTag sortingReviewDto, final List<Tag> tags) {
        final List<TagDto> tagDtos = tags.stream()
                .map(TagDto::toDto)
                .collect(Collectors.toList());
        final Boolean isFavorite = checkingFavorite(sortingReviewDto.getFavorite());

        return new SortingReviewDto(
                sortingReviewDto.getId(),
                sortingReviewDto.getUserName(),
                sortingReviewDto.getProfileImage(),
                sortingReviewDto.getImage(),
                sortingReviewDto.getRating(),
                tagDtos,
                sortingReviewDto.getContent(),
                sortingReviewDto.getRebuy(),
                sortingReviewDto.getFavoriteCount(),
                isFavorite,
                sortingReviewDto.getCreatedAt());
    }

    private static Boolean checkingFavorite(final Boolean favorite) {
        if (Objects.isNull(favorite)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
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

    public Boolean isRebuy() {
        return rebuy;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public Boolean isFavorite() {
        return favorite;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
