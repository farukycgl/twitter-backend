package com.tryst.twitter_backend.service;

import com.tryst.twitter_backend.entity.User;

import java.util.List;

public interface UserService {

    User create(User user);
    List<User> getAll();
    User getById(Long id);
    void delete(Long id);
}
