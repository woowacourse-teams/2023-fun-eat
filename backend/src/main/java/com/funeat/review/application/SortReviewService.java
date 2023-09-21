package com.funeat.review.application;

import static com.funeat.review.exception.ReviewErrorCode.REVIEW_NOT_FOUND;
import static com.funeat.review.exception.ReviewErrorCode.REVIEW_SORTING_OPTION_NOT_FOUND;

import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import com.funeat.review.dto.SortingReviewDto;
import com.funeat.review.exception.ReviewException.ReviewNotFoundException;
import com.funeat.review.exception.ReviewException.ReviewSortingOptionNotFoundException;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.tag.persistence.TagRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SortReviewService {

    private static final Long FIRST = 0L;
    private static final int TEN = 10;
    private static final int ELEVEN = 11;
    private static final PageRequest PAGE = PageRequest.ofSize(ELEVEN);
    private static final String FAVORITE_COUNT_DESC = "favoriteCount,desc";
    private static final String CREATED_AT_DESC = "createdAt,desc";
    private static final String RATING_ASC = "rating,asc";
    private static final String RATING_DESC = "rating,desc";

    private final ReviewRepository reviewRepository;
    private final TagRepository tagRepository;


    public SortReviewService(final ReviewRepository reviewRepository, final TagRepository tagRepository) {
        this.reviewRepository = reviewRepository;
        this.tagRepository = tagRepository;
    }

    public List<SortingReviewDto> execute(final Product product, final Member member,
                                          final Long lastReviewId, final String sort) {
        final List<SortingReviewDto> sortingReviewsWithoutTags = getSortingReviews(product, member, lastReviewId, sort);
        final List<SortingReviewDto> sortingReviews = addTagsToSortingReviews(sortingReviewsWithoutTags);

        return sortingReviews;
    }

    private List<SortingReviewDto> getSortingReviews(final Product product, final Member member,
                                                     final Long lastReviewId,
                                                     final String sort) {
        if (Objects.equals(lastReviewId, FIRST)) {
            return getSortingReviewsFirstPage(product, member, sort);
        }

        final Review lastReview = reviewRepository.findById(lastReviewId)
                .orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND, lastReviewId));

        final List<SortingReviewDto> sortingReviewByEquals = getSortingReviewsEquals(product, member, lastReview, sort);
        if (sortingReviewByEquals.size() >= TEN) {
            return sortingReviewByEquals;
        }

        final List<SortingReviewDto> sortingReviewsByCondition = getSortingReviewsConditions(product, member,
                lastReview, sort);

        return completeSortingReviews(sortingReviewByEquals, sortingReviewsByCondition);
    }

    private List<SortingReviewDto> getSortingReviewsFirstPage(final Product product, final Member member,
                                                              final String sort) {
        if (FAVORITE_COUNT_DESC.equals(sort)) {
            return reviewRepository.sortingReviewsByFavoriteCountDescFirstPage(product, member, PAGE);
        }
        if (CREATED_AT_DESC.equals(sort)) {
            return reviewRepository.sortingReviewsByCreatedAtDescFirstPage(product, member, PAGE);
        }
        if (RATING_ASC.equals(sort)) {
            return reviewRepository.sortingReviewsByRatingAscFirstPage(product, member, PAGE);
        }
        if (RATING_DESC.equals(sort)) {
            return reviewRepository.sortingReviewsByRatingDescFirstPage(product, member, PAGE);
        }
        throw new ReviewSortingOptionNotFoundException(REVIEW_SORTING_OPTION_NOT_FOUND);
    }

    private List<SortingReviewDto> getSortingReviewsEquals(final Product product, final Member member,
                                                           final Review lastReview, final String sort) {
        final Long lastReviewId = lastReview.getId();
        if (FAVORITE_COUNT_DESC.equals(sort)) {
            final Long lastReviewFavoriteCount = lastReview.getFavoriteCount();
            return reviewRepository
                    .sortingReviewsByFavoriteCountDescEquals(product, member, lastReviewFavoriteCount, lastReviewId, PAGE);
        }
        if (CREATED_AT_DESC.equals(sort)) {
            final LocalDateTime lastReviewCreatedAt = lastReview.getCreatedAt();
            return reviewRepository
                    .sortingReviewsByCreatedAtDescEquals(product, member, lastReviewCreatedAt, lastReviewId, PAGE);
        }
        if (RATING_ASC.equals(sort)) {
            final Long lastReviewRating = lastReview.getRating();
            return reviewRepository
                    .sortingReviewsByRatingAscEquals(product, member, lastReviewRating, lastReviewId, PAGE);
        }
        if (RATING_DESC.equals(sort)) {
            final Long lastReviewRating = lastReview.getRating();
            return reviewRepository
                    .sortingReviewsByRatingDescEquals(product, member, lastReviewRating, lastReviewId, PAGE);
        }
        throw new ReviewSortingOptionNotFoundException(REVIEW_SORTING_OPTION_NOT_FOUND);
    }

    private List<SortingReviewDto> getSortingReviewsConditions(final Product product, final Member member,
                                                               final Review lastReview, final String sort) {
        if (FAVORITE_COUNT_DESC.equals(sort)) {
            final Long lastReviewFavoriteCount = lastReview.getFavoriteCount();
            return reviewRepository
                    .sortingReviewByFavoriteCountDescCondition(product, member, lastReviewFavoriteCount, PAGE);
        }
        if (CREATED_AT_DESC.equals(sort)) {
            final LocalDateTime lastReviewCreatedAt = lastReview.getCreatedAt();
            return reviewRepository
                    .sortingReviewByCreatedAtDescCondition(product, member, lastReviewCreatedAt, PAGE);
        }
        if (RATING_ASC.equals(sort)) {
            final Long lastReviewRating = lastReview.getRating();
            return reviewRepository
                    .sortingReviewByRatingAscCondition(product, member, lastReviewRating, PAGE);
        }
        if (RATING_DESC.equals(sort)) {
            final Long lastReviewRating = lastReview.getRating();
            return reviewRepository
                    .sortingReviewByRatingDescCondition(product, member, lastReviewRating, PAGE);
        }
        throw new ReviewSortingOptionNotFoundException(REVIEW_SORTING_OPTION_NOT_FOUND);
    }

    private List<SortingReviewDto> completeSortingReviews(final List<SortingReviewDto> equals,
                                                          final List<SortingReviewDto> condition) {
        final List<SortingReviewDto> sortingReviews = new ArrayList<>();
        sortingReviews.addAll(equals);
        sortingReviews.addAll(condition);

        if (sortingReviews.size() <= ELEVEN) {
            return sortingReviews;
        }
        return sortingReviews.subList(0, ELEVEN);
    }

    private List<SortingReviewDto> addTagsToSortingReviews(final List<SortingReviewDto> sortingReviews) {
        return sortingReviews.stream()
                .map(review -> SortingReviewDto.toDto(review, tagRepository.findTagsByReviewId(review.getId())))
                .collect(Collectors.toList());
    }
}
