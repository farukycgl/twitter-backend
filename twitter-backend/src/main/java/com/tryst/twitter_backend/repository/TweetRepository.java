package com.tryst.twitter_backend.repository;

import com.tryst.twitter_backend.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findByUserId(Long userId);
}
