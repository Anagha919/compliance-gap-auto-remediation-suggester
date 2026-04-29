package com.internship.backend.repository;

import com.internship.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 🔍 Find user by username (used in login)
    Optional<User> findByUsername(String username);

    // 🔍 Check if username exists (used in register)
    boolean existsByUsername(String username);
}