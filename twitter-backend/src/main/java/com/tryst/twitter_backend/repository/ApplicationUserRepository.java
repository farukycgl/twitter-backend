package com.tryst.twitter_backend.repository;

import com.tryst.twitter_backend.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findUserByEmail(String email);
}
