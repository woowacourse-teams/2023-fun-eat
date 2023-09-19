package com.funeat.review.persistence;

import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;

import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import com.funeat.review.dto.SortingReviewDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findReviewsByProduct(final Pageable pageable, final Product product);

    @Query("SELECT new com.funeat.review.dto.SortingReviewDto(r.id, m.nickname, m.profileImage, r.image, r.rating, r.content, r.reBuy, r.favoriteCount, COALESCE(rf.favorite, false), r.createdAt)  "
            + "FROM Review r "
            + "JOIN r.member m "
            + "LEFT JOIN r.reviewFavorites rf "
            + "WHERE r.product = :product "
            + "AND ("
                + "(r.favoriteCount = "
                    + "(SELECT r2.favoriteCount "
                    + "FROM Review r2 "
                    + "WHERE r2.id = :lastReviewId) "
                    + "AND r.id > :lastReviewId "
                + ") "
                + "OR "
                + "(r.favoriteCount < "
                    + "(SELECT r2.favoriteCount "
                    + "FROM Review r2 "
                    + "WHERE r2.id = :lastReviewId)"
                + ")"
            + ")")
    List<SortingReviewDto> findSortingReviewsByFavoriteCountDesc(@Param("product") final Product product,
                                                                 @Param("lastReviewId") final Long lastReviewId,
                                                                 final Pageable pageable);

    List<Review> findTop3ByOrderByFavoriteCountDesc();

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

    Optional<Review> findTopByProductOrderByFavoriteCountDescIdDesc(final Product product);
}
