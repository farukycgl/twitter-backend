package com.tryst.twitter_backend.controller;


import com.tryst.twitter_backend.dto.RegisterRequestUser;
import com.tryst.twitter_backend.entity.ApplicationUser;
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
    public ApplicationUser register(@RequestBody RegisterRequestUser registerRequestUser){
        return registerService.register(registerRequestUser.getFullName(), registerRequestUser.getEmail(), registerRequestUser.getPassword());
    }

}
