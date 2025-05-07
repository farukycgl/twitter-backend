package com.tryst.twitter_backend.service;

import com.tryst.twitter_backend.entity.Tweet;

import java.util.List;

public interface TweetService {

    Tweet create(Tweet tweet);
    List<Tweet> findByUserId(Long id);
    Tweet findById(Long id);
    Tweet update(Long id, Tweet tweet);
    void delete(Long id, Long userId);
}
