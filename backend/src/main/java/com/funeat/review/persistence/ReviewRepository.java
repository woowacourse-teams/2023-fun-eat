package com.funeat.review.persistence;

import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;

import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import com.funeat.review.dto.SortingReviewDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {

    @Query("SELECT new com.funeat.review.dto.SortingReviewDto(r.id, m.nickname, m.profileImage, r.image, r.rating, r.content, r.reBuy, r.favoriteCount, COALESCE(rf.favorite, false), r.createdAt)  "
            + "FROM Review r "
            + "JOIN r.member m "
            + "LEFT JOIN r.reviewFavorites rf ON r.id = rf.review.id AND rf.member = :loginMember "
            + "WHERE r.product = :product "
            + "ORDER BY r.favoriteCount DESC, r.id DESC")
    List<SortingReviewDto> sortingReviewsByFavoriteCountDescFirstPage(@Param("product") final Product product,
                                                                      @Param("loginMember") final Member member,
                                                                      final Pageable pageable);

    @Query("SELECT new com.funeat.review.dto.SortingReviewDto(r.id, m.nickname, m.profileImage, r.image, r.rating, r.content, r.reBuy, r.favoriteCount, COALESCE(rf.favorite, false), r.createdAt)  "
            + "FROM Review r "
            + "JOIN r.member m "
            + "LEFT JOIN r.reviewFavorites rf ON r.id = rf.review.id AND rf.member = :loginMember "
            + "WHERE r.product = :product AND r.favoriteCount = :lastFavoriteCount AND r.id < :lastReviewId "
            + "ORDER BY r.favoriteCount DESC, r.id DESC")
    List<SortingReviewDto> sortingReviewsByFavoriteCountDescEquals(@Param("product") final Product product,
                                                                   @Param("loginMember") final Member member,
                                                                   @Param("lastFavoriteCount") final Long lastReviewFavoriteCount,
                                                                   @Param("lastReviewId") final Long lastReviewId,
                                                                   final Pageable pageable);

    @Query("SELECT new com.funeat.review.dto.SortingReviewDto(r.id, m.nickname, m.profileImage, r.image, r.rating, r.content, r.reBuy, r.favoriteCount, COALESCE(rf.favorite, false), r.createdAt)  "
            + "FROM Review r "
            + "JOIN r.member m "
            + "LEFT JOIN r.reviewFavorites rf ON r.id = rf.review.id AND rf.member = :loginMember "
            + "WHERE r.product = :product AND r.favoriteCount < :lastFavoriteCount "
            + "ORDER BY r.favoriteCount DESC, r.id DESC")
    List<SortingReviewDto> sortingReviewByFavoriteCountDescCondition(@Param("product") final Product product,
                                                                     @Param("loginMember") final Member member,
                                                                     @Param("lastFavoriteCount") final Long lastFavoriteCount,
                                                                     final Pageable pageable);

    @Query("SELECT new com.funeat.review.dto.SortingReviewDto(r.id, m.nickname, m.profileImage, r.image, r.rating, r.content, r.reBuy, r.favoriteCount, COALESCE(rf.favorite, false), r.createdAt)  "
            + "FROM Review r "
            + "JOIN r.member m "
            + "LEFT JOIN r.reviewFavorites rf ON r.id = rf.review.id AND rf.member = :loginMember "
            + "WHERE r.product = :product "
            + "ORDER BY r.createdAt DESC, r.id DESC")
    List<SortingReviewDto> sortingReviewsByCreatedAtDescFirstPage(@Param("product") final Product product,
                                                                  @Param("loginMember") final Member member,
                                                                  final Pageable pageable);

    @Query("SELECT new com.funeat.review.dto.SortingReviewDto(r.id, m.nickname, m.profileImage, r.image, r.rating, r.content, r.reBuy, r.favoriteCount, COALESCE(rf.favorite, false), r.createdAt)  "
            + "FROM Review r "
            + "JOIN r.member m "
            + "LEFT JOIN r.reviewFavorites rf ON r.id = rf.review.id AND rf.member = :loginMember "
            + "WHERE r.product = :product AND r.createdAt = :lastReviewCreatedAt AND r.id < :lastReviewId "
            + "ORDER BY r.createdAt DESC, r.id DESC")
    List<SortingReviewDto> sortingReviewsByCreatedAtDescEquals(@Param("product") final Product product,
                                                               @Param("loginMember") final Member member,
                                                               @Param("lastReviewCreatedAt") final LocalDateTime createdAt,
                                                               @Param("lastReviewId") final Long lastReviewId,
                                                               final Pageable pageable);

    @Query("SELECT new com.funeat.review.dto.SortingReviewDto(r.id, m.nickname, m.profileImage, r.image, r.rating, r.content, r.reBuy, r.favoriteCount, COALESCE(rf.favorite, false), r.createdAt)  "
            + "FROM Review r "
            + "JOIN r.member m "
            + "LEFT JOIN r.reviewFavorites rf ON r.id = rf.review.id AND rf.member = :loginMember "
            + "WHERE r.product = :product AND r.createdAt < :lastReviewCreatedAt "
            + "ORDER BY r.createdAt DESC, r.id DESC")
    List<SortingReviewDto> sortingReviewByCreatedAtDescCondition(@Param("product") final Product product,
                                                                 @Param("loginMember") final Member member,
                                                                 @Param("lastReviewCreatedAt") final LocalDateTime createdAt,
                                                                 final Pageable pageable);

    @Query("SELECT new com.funeat.review.dto.SortingReviewDto(r.id, m.nickname, m.profileImage, r.image, r.rating, r.content, r.reBuy, r.favoriteCount, COALESCE(rf.favorite, false), r.createdAt)  "
            + "FROM Review r "
            + "JOIN r.member m "
            + "LEFT JOIN r.reviewFavorites rf ON r.id = rf.review.id AND rf.member = :loginMember "
            + "WHERE r.product = :product "
            + "ORDER BY r.rating ASC, r.id DESC")
    List<SortingReviewDto> sortingReviewsByRatingAscFirstPage(@Param("product") final Product product,
                                                              @Param("loginMember") final Member member,
                                                              final Pageable pageable);

    @Query("SELECT new com.funeat.review.dto.SortingReviewDto(r.id, m.nickname, m.profileImage, r.image, r.rating, r.content, r.reBuy, r.favoriteCount, COALESCE(rf.favorite, false), r.createdAt)  "
            + "FROM Review r "
            + "JOIN r.member m "
            + "LEFT JOIN r.reviewFavorites rf ON r.id = rf.review.id AND rf.member = :loginMember "
            + "WHERE r.product = :product AND r.rating = :lastReviewRating AND r.id = :lastReviewId "
            + "ORDER BY r.rating ASC, r.id DESC")
    List<SortingReviewDto> sortingReviewsByRatingAscEquals(@Param("product") final Product product,
                                                           @Param("loginMember") final Member member,
                                                           @Param("lastReviewRating") final Long lastReviewRating,
                                                           @Param("lastReviewId") final Long lastReviewId,
                                                           final Pageable pageable);

    @Query("SELECT new com.funeat.review.dto.SortingReviewDto(r.id, m.nickname, m.profileImage, r.image, r.rating, r.content, r.reBuy, r.favoriteCount, COALESCE(rf.favorite, false), r.createdAt)  "
            + "FROM Review r "
            + "JOIN r.member m "
            + "LEFT JOIN r.reviewFavorites rf ON r.id = rf.review.id AND rf.member = :loginMember "
            + "WHERE r.product = :product AND r.rating > :lastReviewRating "
            + "ORDER BY r.rating ASC, r.id DESC")
    List<SortingReviewDto> sortingReviewByRatingAscCondition(@Param("product") final Product product,
                                                             @Param("loginMember") final Member member,
                                                             @Param("lastReviewRating") final Long lastReviewRating,
                                                             final Pageable pageable);

    @Query("SELECT new com.funeat.review.dto.SortingReviewDto(r.id, m.nickname, m.profileImage, r.image, r.rating, r.content, r.reBuy, r.favoriteCount, COALESCE(rf.favorite, false), r.createdAt)  "
            + "FROM Review r "
            + "JOIN r.member m "
            + "LEFT JOIN r.reviewFavorites rf ON r.id = rf.review.id AND rf.member = :loginMember "
            + "WHERE r.product = :product "
            + "ORDER BY r.rating DESC, r.id DESC")
    List<SortingReviewDto> sortingReviewsByRatingDescFirstPage(@Param("product") final Product product,
                                                               @Param("loginMember") final Member member,
                                                               final Pageable pageable);

    @Query("SELECT new com.funeat.review.dto.SortingReviewDto(r.id, m.nickname, m.profileImage, r.image, r.rating, r.content, r.reBuy, r.favoriteCount, COALESCE(rf.favorite, false), r.createdAt)  "
            + "FROM Review r "
            + "JOIN r.member m "
            + "LEFT JOIN r.reviewFavorites rf ON r.id = rf.review.id AND rf.member = :loginMember "
            + "WHERE r.product = :product AND r.rating = :lastReviewRating AND r.id = :lastReviewId "
            + "ORDER BY r.rating DESC, r.id DESC")
    List<SortingReviewDto> sortingReviewsByRatingDescEquals(@Param("product") final Product product,
                                                            @Param("loginMember") final Member member,
                                                            @Param("lastReviewRating") final Long lastReviewRating,
                                                            @Param("lastReviewId") final Long lastReviewId,
                                                            final Pageable pageable);

    @Query("SELECT new com.funeat.review.dto.SortingReviewDto(r.id, m.nickname, m.profileImage, r.image, r.rating, r.content, r.reBuy, r.favoriteCount, COALESCE(rf.favorite, false), r.createdAt)  "
            + "FROM Review r "
            + "JOIN r.member m "
            + "LEFT JOIN r.reviewFavorites rf ON r.id = rf.review.id AND rf.member = :loginMember "
            + "WHERE r.product = :product AND r.rating < :lastReviewRating "
            + "ORDER BY r.rating DESC, r.id DESC")
    List<SortingReviewDto> sortingReviewByRatingDescCondition(@Param("product") final Product product,
                                                              @Param("loginMember") final Member member,
                                                              @Param("lastReviewRating") final Long lastReviewRating,
                                                              final Pageable pageable);

    List<Review> findTop3ByOrderByFavoriteCountDescIdDesc();

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
