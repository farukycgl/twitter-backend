package com.tryst.twitter_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterRequestUser {

    private String fullName;
    private String email;
    private String password;
}
