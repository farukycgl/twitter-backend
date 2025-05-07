package com.tryst.twitter_backend.repository;

import com.tryst.twitter_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
