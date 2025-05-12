package com.tryst.twitter_backend.controller;

import com.tryst.twitter_backend.dto.RegisterRequestDto;
import com.tryst.twitter_backend.dto.RegisterResponseDto;

import com.tryst.twitter_backend.service.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final RegisterService registerService;

    @PostMapping("/register")
    public RegisterResponseDto register(@RequestBody RegisterRequestDto registerRequestDto){

        registerService.register(registerRequestDto.getEmail(), registerRequestDto.getPassword());
        return new RegisterResponseDto(registerRequestDto.getEmail(), "Kullanıcı, başarılı bir şekilde oluşturuldu.");
    }

}
