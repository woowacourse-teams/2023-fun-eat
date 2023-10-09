package com.funeat.review.persistence;

import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.tag.domain.Tag;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long> {

    @Query("SELECT rt.tag, COUNT(rt.tag) AS cnt "
            + "FROM ReviewTag rt "
            + "JOIN Review r ON rt.review.id = r.id "
            + "WHERE r.product.id = :productId "
            + "GROUP BY rt.tag "
            + "ORDER BY cnt DESC")
    List<Tag> findTop3TagsByReviewIn(final Long productId, final Pageable pageable);

    void deleteByReview(final Review review);
}
