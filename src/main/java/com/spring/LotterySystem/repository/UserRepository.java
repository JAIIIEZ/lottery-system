package com.spring.LotterySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.LotterySystem.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
