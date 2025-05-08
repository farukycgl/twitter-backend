package com.tryst.twitter_backend.controller;

import com.tryst.twitter_backend.entity.User;
import com.tryst.twitter_backend.service.UserService;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/tryst/twitter/api/user")
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Validated @RequestBody User user){
        return userService.create(user);
    }

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getByUserId(@Positive @PathVariable("id") Long id){
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUserId(@Positive @PathVariable Long id){
        userService.delete(id);
    }

}
