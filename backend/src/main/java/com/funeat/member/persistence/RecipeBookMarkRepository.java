package com.funeat.member.persistence;

import com.funeat.member.domain.bookmark.RecipeBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeBookMarkRepository extends JpaRepository<RecipeBookmark, Long> {
}
