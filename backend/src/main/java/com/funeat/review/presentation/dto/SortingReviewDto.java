package com.funeat.review.presentation.dto;

import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.tag.dto.TagDto;
import java.util.List;
import java.util.stream.Collectors;

public class SortingReviewDto {

    private final Long id;
    private final String userName;
    private final String profileImage;
    private final String image;
    private final Long rating;
    private final List<TagDto> tags;
    private final String content;
    private final boolean rebuy;
    private final Long favoriteCount;
    private final boolean favorite;

    public SortingReviewDto(final Long id, final String userName, final String profileImage, final String image,
                            final Long rating, final List<TagDto> tags, final String content, final boolean rebuy,
                            final Long favoriteCount, final boolean favorite) {
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
    }

    public static SortingReviewDto toDto(final Review review) {
        return new SortingReviewDto(
                review.getId(),
                review.getMember().getNickname(),
                review.getMember().getProfileImage(),
                review.getImage(),
                review.getRating(),
                findTagDtos(review),
                review.getContent(),
                review.getReBuy(),
                review.getFavoriteCount(),
                findReviewFavoriteChecked(review)
        );
    }

    private static List<TagDto> findTagDtos(final Review review) {
        return review.getReviewTags().stream()
                .map(ReviewTag::getTag)
                .map(TagDto::toDto)
                .collect(Collectors.toList());
    }

    private static boolean findReviewFavoriteChecked(final Review review) {
        return review.getReviewFavorites()
                .stream()
                .filter(reviewFavorite -> reviewFavorite.getReview().equals(review))
                .filter(reviewFavorite -> reviewFavorite.getMember().equals(review.getMember()))
                .findFirst()
                .map(ReviewFavorite::getChecked)
                .orElse(false);
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

    public boolean isFavorite() {
        return favorite;
    }
}
