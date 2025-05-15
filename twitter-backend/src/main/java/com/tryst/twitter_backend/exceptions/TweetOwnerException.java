package com.tryst.twitter_backend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TweetOwnerException extends TwitterException {
    public TweetOwnerException(String message) {
        super(message, HttpStatus.FORBIDDEN); // 403
    }
}
