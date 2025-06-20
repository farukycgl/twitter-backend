package com.tryst.twitter_backend.service;

import com.tryst.twitter_backend.entity.Role;
import com.tryst.twitter_backend.entity.User;
import com.tryst.twitter_backend.exceptions.UserAlreadyRegisteredException;
import com.tryst.twitter_backend.repository.RoleRepository;
import com.tryst.twitter_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final RoleRepository roleRepository;


    public User register(String fullName ,String email, String password){

        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if(userOptional.isPresent())
            throw new UserAlreadyRegisteredException("Kayıtlı email!");

        // password'ü şifrele
        String encodedPassword = passwordEncoder.encode(password);

        Role userRole = roleRepository.findRoleByAuthority("USER")
                .orElseThrow(() -> new RuntimeException("USER rolü veritabanında bulunamadı! Lütfen rolü ekleyin."));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRoles(roles);

        return userRepository.save(user);
    }
}
