package com.funeat.review.persistence;

import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findReviewsByProduct(final Pageable pageable, final Product product);

    List<Review> findTop3ByOrderByFavoriteCountDesc();

    Long countByProduct(final Product product);

    Page<Review> findReviewsByMember(final Member findMember, final Pageable pageable);

    @Query("SELECT r "
            + "FROM Review r "
            + "LEFT JOIN r.product p "
            + "WHERE p.id = :id AND r.image IS NOT NULL "
            + "ORDER BY r.favoriteCount DESC, r.id DESC")
    List<Review> findPopularImage(@Param("id") final Long productId, final Pageable pageable);
}
