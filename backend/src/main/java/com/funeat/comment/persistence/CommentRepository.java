package com.funeat.comment.persistence;

import com.funeat.comment.domain.Comment;
import com.funeat.common.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, BaseRepository<Comment, Long> {
}
