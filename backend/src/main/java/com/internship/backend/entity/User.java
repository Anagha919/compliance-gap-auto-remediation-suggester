package com.internship.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @JsonIgnore   // 🔐 hide password in response
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;   // 🔥 ROLE field (IMPORTANT)
}