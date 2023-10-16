package com.funeat.review.application;

import static com.funeat.review.exception.ReviewErrorCode.REVIEW_NOT_FOUND;

import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import com.funeat.review.dto.SortingReviewDto;
import com.funeat.review.dto.SortingReviewDtoWithoutTag;
import com.funeat.review.dto.SortingReviewRequest;
import com.funeat.review.exception.ReviewException.ReviewNotFoundException;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.specification.SortingReviewSpecification;
import com.funeat.tag.persistence.TagRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SortingReviewService {

    private static final int FIRST_PAGE = 0;

    private final ReviewRepository reviewRepository;
    private final TagRepository tagRepository;

    public SortingReviewService(final ReviewRepository reviewRepository, final TagRepository tagRepository) {
        this.reviewRepository = reviewRepository;
        this.tagRepository = tagRepository;
    }

    public List<SortingReviewDto> getSortingReviews(final Member member, final Product product,
                                                    final SortingReviewRequest request) {
        final Long lastReviewId = request.getLastReviewId();
        final String sortOption = request.getSort();

        final Specification<Review> specification = getSortingSpecification(product, sortOption, lastReviewId);
        final List<SortingReviewDtoWithoutTag> sortingReviewDtoWithoutTags = reviewRepository.getSortingReview(member,
                specification, sortOption);
        final List<SortingReviewDto> sortingReviewDtos = addTagsToSortingReviews(sortingReviewDtoWithoutTags);

        return sortingReviewDtos;
    }

    private List<SortingReviewDto> addTagsToSortingReviews(
            final List<SortingReviewDtoWithoutTag> sortingReviewDtoWithoutTags) {
        return sortingReviewDtoWithoutTags.stream()
                .map(reviewDto -> SortingReviewDto.toDto(reviewDto,
                        tagRepository.findTagsByReviewId(reviewDto.getId())))
                .collect(Collectors.toList());
    }

    private Specification<Review> getSortingSpecification(final Product product, final String sortOption,
                                                          final Long lastReviewId) {
        if (lastReviewId == FIRST_PAGE) {
            return SortingReviewSpecification.sortingFirstPageBy(product);
        }

        final Review lastReview = reviewRepository.findById(lastReviewId)
                .orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND, lastReviewId));

        return SortingReviewSpecification.sortingBy(product, sortOption, lastReview);
    }
}
