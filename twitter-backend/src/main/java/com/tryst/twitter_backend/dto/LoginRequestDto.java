package com.tryst.twitter_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginRequestDto {

    private String email;
    private String password;
}
