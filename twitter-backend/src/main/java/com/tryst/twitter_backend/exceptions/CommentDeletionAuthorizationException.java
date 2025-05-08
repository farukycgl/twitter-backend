package com.tryst.twitter_backend.exceptions;

import org.springframework.http.HttpStatus;

public class CommentDeletionAuthorizationException extends TwitterException {
    public CommentDeletionAuthorizationException(String message) {
        super(message, HttpStatus.FORBIDDEN); //403
    }
}
