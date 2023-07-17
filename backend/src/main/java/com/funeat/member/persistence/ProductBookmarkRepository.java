package com.funeat.member.persistence;

import com.funeat.member.domain.bookmark.ProductBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBookmarkRepository extends JpaRepository<ProductBookmark, Long> {
}
