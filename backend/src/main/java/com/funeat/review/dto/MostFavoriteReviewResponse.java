package com.funeat.review.dto;

import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.tag.dto.TagDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MostFavoriteReviewResponse {

    private Long id;
    private String userName;
    private String profileImage;
    private String image;
    private Long rating;
    private List<TagDto> tags;
    private String content;
    private Boolean rebuy;
    private Long favoriteCount;
    private Boolean favorite;
    private LocalDateTime createdAt;

    public MostFavoriteReviewResponse() {
    }

    public MostFavoriteReviewResponse(final Long id, final String userName, final String profileImage,
                                      final String image, final Long rating, final List<TagDto> tags,
                                      final String content, final boolean rebuy, final Long favoriteCount,
                                      final boolean favorite, final LocalDateTime createdAt) {
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

    public static MostFavoriteReviewResponse toEmptyResponse() {
        return new MostFavoriteReviewResponse();
    }

    public static MostFavoriteReviewResponse toResponse(final Review review) {
        return new MostFavoriteReviewResponse(
                review.getId(),
                review.getMember().getNickname(),
                review.getMember().getProfileImage(),
                review.getImage(),
                review.getRating(),
                findTagDtos(review),
                review.getContent(),
                review.getReBuy(),
                review.getFavoriteCount(),
                false,
                review.getCreatedAt()
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
