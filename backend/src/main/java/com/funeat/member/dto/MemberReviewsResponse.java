package com.funeat.member.dto;

import com.funeat.review.presentation.dto.SortingReviewsPageDto;
import java.util.List;

public class MemberReviewsResponse {

    private final SortingReviewsPageDto page;
    private final List<MemberReviewDto> reviews;

    private MemberReviewsResponse(final SortingReviewsPageDto page, final List<MemberReviewDto> reviews) {
        this.page = page;
        this.reviews = reviews;
    }

    public static MemberReviewsResponse toResponse(final SortingReviewsPageDto page,
                                                   final List<MemberReviewDto> reviews) {
        return new MemberReviewsResponse(page, reviews);
    }

    public SortingReviewsPageDto getPage() {
        return page;
    }

    public List<MemberReviewDto> getReviews() {
        return reviews;
    }
}
