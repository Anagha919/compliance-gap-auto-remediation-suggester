package com.internship.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "compliance_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ComplianceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔥 Title
    @NotBlank(message = "Title cannot be empty")
    private String title;

    // 🔥 Description
    private String description;

    // 🔥 Status (OPEN / CLOSED)
    @NotBlank(message = "Status cannot be empty")
    private String status;

    // 🔥 Severity (LOW / MEDIUM / HIGH)
    @NotBlank(message = "Severity cannot be empty")
    private String severity;

    // 🔥 Deadline (for alerts)
    private LocalDate deadline;

    // 🔥 Created by
    private String createdBy;

    // 🔥 Created at (auto)
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // 🔥 Updated at (auto)
    @LastModifiedDate
    private LocalDateTime updatedAt;
}