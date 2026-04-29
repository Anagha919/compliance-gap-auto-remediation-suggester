package com.internship.backend.controller;

import com.internship.backend.entity.User;
import com.internship.backend.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 🔥 REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        User createdUser = authService.register(user);

        return ResponseEntity.ok(createdUser);
    }

    // 🔥 LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        String token = authService.login(
                user.getUsername(),
                user.getPassword()
        );

        return ResponseEntity.ok(token);
    }

    // 🔥 REFRESH TOKEN
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam String username) {

        String newToken = authService.refreshToken(username);

        return ResponseEntity.ok(newToken);
    }
}