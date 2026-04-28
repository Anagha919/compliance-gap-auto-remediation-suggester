package com.internship.backend.service;

import com.internship.backend.entity.User;
import com.internship.backend.exception.BadRequestException;
import com.internship.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔥 REGISTER USER
    public User register(User user) {

        // ✅ basic validation
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new BadRequestException("Username cannot be empty");
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new BadRequestException("Password cannot be empty");
        }

        // ✅ check if user already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists");
        }

        // 🔐 encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 🔥 assign default role
        user.setRole("ROLE_USER");

        return userRepository.save(user);
    }

    // 🔍 FIND USER
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));
    }
}