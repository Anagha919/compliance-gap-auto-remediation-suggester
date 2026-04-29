package com.internship.backend.service;

import com.internship.backend.entity.User;
import com.internship.backend.exception.BadRequestException;
import com.internship.backend.repository.UserRepository;
import com.internship.backend.config.JwtUtil;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 🔥 REGISTER
    public User register(User user) {

        // Optional pre-check (fast feedback)
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        try {
            return userRepository.save(user);

        } catch (DataIntegrityViolationException e) {
            // 🔐 Handles race condition
            throw new BadRequestException("Username already exists");
        }
    }

    // 🔥 LOGIN
    public String login(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new BadRequestException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Invalid username or password");
        }

        // 🔐 Generate JWT with role
        return jwtUtil.generateToken(user.getUsername(), user.getRole());
    }

    // 🔥 REFRESH TOKEN (simple version)
    public String refreshToken(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new BadRequestException("User not found"));

        return jwtUtil.generateToken(user.getUsername(), user.getRole());
    }
}