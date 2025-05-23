package com.tryst.twitter_backend.controller;

import com.tryst.twitter_backend.dto.RegisterRequestDto;
import com.tryst.twitter_backend.dto.RegisterResponseDto;

import com.tryst.twitter_backend.entity.User;
import com.tryst.twitter_backend.service.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final RegisterService registerService;

    @PostMapping("/register")
    public User register(@Validated @RequestBody RegisterRequestDto registerRequestDto){

        return registerService.register(registerRequestDto.getFullName(), registerRequestDto.getEmail(), registerRequestDto.getPassword());

    }

}
