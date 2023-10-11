package com.funeat.review.persistence;

import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findReviewsByProduct(final Pageable pageable, final Product product);

    Long countByProduct(final Product product);

    Page<Review> findReviewsByMember(final Member findMember, final Pageable pageable);

    @Lock(PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Review r WHERE r.id=:id")
    Optional<Review> findByIdForUpdate(final Long id);

    @Query("SELECT r "
            + "FROM Review r "
            + "LEFT JOIN r.product p "
            + "WHERE p.id = :id AND r.image != '' "
            + "ORDER BY r.favoriteCount DESC, r.id DESC")
    List<Review> findPopularReviewWithImage(@Param("id") final Long productId, final Pageable pageable);

    List<Review> findReviewsByFavoriteCountGreaterThanEqual(final Long favoriteCount);
}
