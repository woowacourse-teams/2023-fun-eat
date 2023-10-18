package com.funeat.tag.persistence;

import com.funeat.tag.domain.Tag;
import com.funeat.tag.domain.TagType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findTagsByIdIn(final List<Long> tagIds);

    List<Tag> findTagsByTagType(final TagType tagType);

    @Query("SELECT t "
            + "FROM ReviewTag rt "
            + "JOIN rt.tag t "
            + "WHERE rt.review.id = :reviewId")
    List<Tag> findTagsByReviewId(@Param("reviewId") final Long reviewId);
}
