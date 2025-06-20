package com.tryst.twitter_backend.dto;




public record TweetResponseDto(Long id , String content, UserResponseDto tweetUser) {
}
