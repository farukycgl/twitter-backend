package com.tryst.twitter_backend.service;

import com.tryst.twitter_backend.entity.Comment;

public interface CommentService {

    Comment create(Long tweetId ,Comment comment, Long userId);
    Comment update(Long id, Comment comment);
    void delete(Long id, Long userId);
}
