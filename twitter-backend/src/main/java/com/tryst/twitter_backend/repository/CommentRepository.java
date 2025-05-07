package com.tryst.twitter_backend.repository;

import com.tryst.twitter_backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
