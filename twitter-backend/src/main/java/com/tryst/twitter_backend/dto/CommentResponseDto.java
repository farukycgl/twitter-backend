package com.tryst.twitter_backend.dto;

import com.tryst.twitter_backend.entity.User;

public record CommentResponseDto(String content, TweetResponseDto tweet, UserResponseDto commentUser) {
}
