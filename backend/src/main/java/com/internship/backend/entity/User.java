package com.internship.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users",
       uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔐 Unique username (DB-level safety)
    @NotBlank(message = "Username cannot be empty")
    @Column(nullable = false, unique = true)
    private String username;

    // 🔐 Encrypted password
    @NotBlank(message = "Password cannot be empty")
    private String password;

    // 🔐 Role (ROLE_USER / ROLE_ADMIN)
    @Column(nullable = false)
    private String role;
}