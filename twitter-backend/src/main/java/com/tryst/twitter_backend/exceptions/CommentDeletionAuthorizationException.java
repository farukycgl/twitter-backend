package com.tryst.twitter_backend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommentDeletionAuthorizationException extends TwitterException {
    public CommentDeletionAuthorizationException(String message) {
        super(message, HttpStatus.FORBIDDEN); //403
    }
}
