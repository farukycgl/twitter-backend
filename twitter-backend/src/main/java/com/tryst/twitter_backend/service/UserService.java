package com.tryst.twitter_backend.service;

import com.tryst.twitter_backend.dto.UserResponseDto;
import com.tryst.twitter_backend.entity.User;

import java.util.List;

public interface UserService {

    User create(UserResponseDto userResponseDto);
    List<User> getAll();
    User getById(Long id);
    void delete(Long id);
}
