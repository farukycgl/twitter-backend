package com.tryst.twitter_backend.controller;

import com.tryst.twitter_backend.dto.UserResponseDto;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@Validated @RequestBody UserResponseDto userResponseDto){
        User user = userService.create(userResponseDto);
        return new UserResponseDto(user.getId(), user.getUserName(), user.getEmail());
    }

    @GetMapping
    public List<UserResponseDto> getAllUser(){
        return userService.getAll()
                .stream()
                .map((user) -> new UserResponseDto(
                        user.getId(),
                        user.getUserName(),
                        user.getEmail()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponseDto getByUserId(@Positive @PathVariable("id") Long id){
        User user = userService.getById(id);
        return new UserResponseDto(user.getId(), user.getUserName(), user.getEmail());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUserId(@Positive @PathVariable Long id){
        userService.delete(id);
    }

}
