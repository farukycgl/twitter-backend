package com.tryst.twitter_backend.service;

import com.tryst.twitter_backend.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public LoginService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return applicationUserRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı adı hatalı."));

    }
}
