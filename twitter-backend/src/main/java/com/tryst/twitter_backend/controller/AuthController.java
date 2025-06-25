package com.tryst.twitter_backend.controller;

import com.tryst.twitter_backend.dto.LoginRequestDto;
import com.tryst.twitter_backend.dto.LoginResponseDto;
import com.tryst.twitter_backend.dto.RegisterRequestDto;
import com.tryst.twitter_backend.dto.RegisterResponseDto;

import com.tryst.twitter_backend.entity.User;
import com.tryst.twitter_backend.security.JwtUtil;
import com.tryst.twitter_backend.service.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterService registerService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    @PostMapping("/register")
    public User register(@Validated @RequestBody RegisterRequestDto registerRequestDto){

        return registerService.register(registerRequestDto.getFullName(), registerRequestDto.getEmail(), registerRequestDto.getPassword());

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );

      String token = jwtUtil.generateToken(loginRequestDto.getEmail());

      return ResponseEntity.ok(new LoginResponseDto(token));
    }

}
