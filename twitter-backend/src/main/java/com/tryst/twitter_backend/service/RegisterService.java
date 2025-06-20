package com.tryst.twitter_backend.service;

import com.tryst.twitter_backend.entity.User;

public interface RegisterService {

    User register(String fullName ,String email, String password);
}
