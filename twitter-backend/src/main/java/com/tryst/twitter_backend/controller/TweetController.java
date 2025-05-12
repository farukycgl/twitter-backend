package com.tryst.twitter_backend.controller;

import com.tryst.twitter_backend.dto.TweetRequestDto;
import com.tryst.twitter_backend.dto.TweetResponseDto;
import com.tryst.twitter_backend.dto.UserResponseDto;
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
@RequestMapping("/tweets")
public class TweetController {

    @Autowired
    private final TweetService tweetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto createTweet(@Validated @RequestBody TweetRequestDto tweetRequestDto){
       return tweetService.create(tweetRequestDto);
    }

    @GetMapping
    public List<TweetResponseDto> getAllTweets(){
        return tweetService.findAllTweets()
                .stream()
                .map((tweet)-> new TweetResponseDto(tweet.getId(), tweet.getContent(),
                                        new UserResponseDto(tweet.getUser().getId(), tweet.getUser().getFullName(), tweet.getUser().getEmail())
                ))
                .toList();
    }

    @GetMapping("/user/{userId}")
    public List<TweetResponseDto> getTweetsByUserId(@Positive @PathVariable("userId") Long userId){
        return tweetService.findByUserId(userId)
                .stream()
                .map((tweet -> new TweetResponseDto(tweet.getId(), tweet.getContent(),
                                        new UserResponseDto(tweet.getUser().getId(), tweet.getUser().getFullName(), tweet.getUser().getEmail()))
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public TweetResponseDto getById(@Positive @PathVariable Long id){
        Tweet tweet = tweetService.findById(id);
        return new TweetResponseDto(tweet.getId(), tweet.getContent(),
                new UserResponseDto(tweet.getUser().getId(), tweet.getUser().getFullName(), tweet.getUser().getEmail()));
    }

    @PutMapping("/{id}")
    public TweetResponseDto update(@Positive @PathVariable("id") Long id,
                        @Validated @RequestBody TweetRequestDto tweetRequestDto){

        return tweetService.update(id, tweetRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable("id") Long id,
                       @RequestParam Long userId){

        tweetService.delete(id, userId);
    }
}
