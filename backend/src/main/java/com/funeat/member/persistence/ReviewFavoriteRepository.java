package com.funeat.member.persistence;

import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.review.domain.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewFavoriteRepository extends JpaRepository<ReviewFavorite, Long> {

    Optional<ReviewFavorite> findByMemberAndReview(final Member member, final Review review);

    void deleteByReview(final Review review);

    List<ReviewFavorite> findByReview(final Review review);
}
