package com.tryst.twitter_backend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponse {

    private String message;
    private int status;
    private LocalDateTime localDateTime;
}
