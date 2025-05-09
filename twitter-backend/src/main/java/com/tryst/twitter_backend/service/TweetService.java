package com.tryst.twitter_backend.service;

import com.tryst.twitter_backend.dto.TweetRequestDto;
import com.tryst.twitter_backend.dto.TweetResponseDto;
import com.tryst.twitter_backend.entity.Tweet;

import java.util.List;

public interface TweetService {

    TweetResponseDto create(TweetRequestDto tweetRequestDto);
    List<Tweet> findAllTweets();
    List<Tweet> findByUserId(Long id);
    Tweet findById(Long id);
    TweetResponseDto update(Long id, TweetRequestDto tweetRequestDto);
    void delete(Long id, Long userId);
}
