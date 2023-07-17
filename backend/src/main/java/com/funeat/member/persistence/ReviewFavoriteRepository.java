package com.funeat.member.persistence;

import com.funeat.member.domain.favorite.ReviewFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewFavoriteRepository extends JpaRepository<ReviewFavorite, Long> {
}
