package com.funeat.member.persistence;

import com.funeat.member.domain.favorite.RecipeFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeFavoriteRepository extends JpaRepository<RecipeFavorite, Long> {
}
