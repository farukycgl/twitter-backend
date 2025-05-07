package com.tryst.twitter_backend.repository;

import com.tryst.twitter_backend.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
