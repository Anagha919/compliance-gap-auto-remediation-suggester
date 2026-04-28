package com.internship.backend.controller;

import com.internship.backend.config.config.JwtUtil;
import com.internship.backend.entity.User;
import com.internship.backend.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {

        User dbUser = authService.findByUsername(user.getUsername());

        // 🔐 Compare encrypted password
        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(dbUser.getUsername());

        return Map.of("token", token);
    }

    // ✅ REFRESH TOKEN
    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestHeader("Authorization") String header) {

        String token = header.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);

        String newToken = jwtUtil.generateToken(username);

        return Map.of("token", newToken);
    }
}