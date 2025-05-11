package com.tryst.twitter_backend.service;

import com.tryst.twitter_backend.entity.ApplicationUser;
import com.tryst.twitter_backend.repository.ApplicationUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegisterService {

    @Autowired
    private final ApplicationUserRepository applicationUserRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;


    public ApplicationUser register(String fullName, String email, String password){

        // ilk başta password ü şifrele
        String encodedPassword = passwordEncoder.encode(password);

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setFullName(fullName);
        applicationUser.setEmail(email);
        applicationUser.setPassword(encodedPassword);

        return applicationUserRepository.save(applicationUser);
    }
}
