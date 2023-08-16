package com.funeat.member.dto;

import com.funeat.common.dto.PageDto;
import java.util.List;

public class MemberReviewsResponse {

    private final PageDto page;
    private final List<MemberReviewDto> reviews;

    private MemberReviewsResponse(final PageDto page, final List<MemberReviewDto> reviews) {
        this.page = page;
        this.reviews = reviews;
    }

    public static MemberReviewsResponse toResponse(final PageDto page, final List<MemberReviewDto> reviews) {
        return new MemberReviewsResponse(page, reviews);
    }

    public PageDto getPage() {
        return page;
    }

    public List<MemberReviewDto> getReviews() {
        return reviews;
    }
}
