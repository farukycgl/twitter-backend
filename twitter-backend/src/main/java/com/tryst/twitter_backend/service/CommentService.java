package com.tryst.twitter_backend.service;

import com.tryst.twitter_backend.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> findById(Long id);

    List<Comment> findAllComment();

    Comment create(Long tweetId, Comment comment);

    Comment update(Long id, Comment comment);

    void delete(Long id);
}
