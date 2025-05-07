package com.tryst.twitter_backend.exceptions;

import org.springframework.http.HttpStatus;

public class TweetOwnerException extends TwitterException {
    public TweetOwnerException(String message) {
        super(message, HttpStatus.FORBIDDEN); // 403
    }
}
