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

        Optional<Role> userRole = roleRepository.findRoleByAuthority("USER");

        if(userRole.isEmpty()) {
            Role role = new Role();
            role.setAuthority("USER");

            userRole = Optional.of(roleRepository.save(role));
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRoles(Set.of(userRole.get()));

        return userRepository.save(user);
    }
}
