package com.tryst.twitter_backend.controller;

import com.tryst.twitter_backend.entity.Tweet;
import com.tryst.twitter_backend.service.TweetService;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/tryst/twitter/api/tweet")
public class TweetController {

    @Autowired
    private final TweetService tweetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tweet create(@Validated @RequestBody Tweet tweet){
        return tweetService.create(tweet);
    }

    @GetMapping("/user/{userId}")
    public List<Tweet> getByUserId(@Positive @PathVariable("id") Long userId){
        return tweetService.findByUserId(userId);
    }

    @GetMapping("/{id}")
    public Tweet getById(@Positive @PathVariable Long id){
        return tweetService.findById(id);
    }

    @PutMapping("/{id}")
    public Tweet update(@Positive @PathVariable("id") Long id,
                        @Validated @RequestBody Tweet tweet){

        return tweetService.update(id, tweet);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable("id") Long id,
                       @RequestParam Long userId){

        tweetService.delete(id, userId);
    }
}
