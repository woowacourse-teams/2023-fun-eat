package com.funeat.review.persistence;

import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findReviewsByProduct(final Pageable pageable, final Product product);

    List<Review> findTop3ByOrderByFavoriteCountDesc();

    Long countByProduct(final Product product);

    Page<Review> findReviewsByMember(final Member findMember, final Pageable pageable);

    Optional<Review> findTopByProductOrderByFavoriteCountDesc(final Product product);
}
