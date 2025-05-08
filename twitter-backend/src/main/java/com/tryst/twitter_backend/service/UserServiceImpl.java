package com.tryst.twitter_backend.service;


import com.tryst.twitter_backend.entity.User;
import com.tryst.twitter_backend.exceptions.UserNotFoundException;
import com.tryst.twitter_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(()-> new UserNotFoundException(id + " id'li kullanıcı bulunamadı."));
    }

    @Override
    public void delete(Long id) {
         userRepository.deleteById(id);
    }
}
