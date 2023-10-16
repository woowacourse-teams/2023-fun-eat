package com.funeat.review.persistence;

import com.funeat.member.domain.Member;
import com.funeat.review.domain.Review;
import com.funeat.review.dto.SortingReviewDtoWithoutTag;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public interface ReviewCustomRepository {

    List<SortingReviewDtoWithoutTag> getSortingReview(final Member loginMember,
                                                      final Specification<Review> specification,
                                                      final String sortField);
}
