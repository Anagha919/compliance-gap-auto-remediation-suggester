package com.internship.backend.repository;

import com.internship.backend.entity.ComplianceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplianceRecordRepository extends JpaRepository<ComplianceRecord, Long> {

    List<ComplianceRecord> findByStatus(String status);

    List<ComplianceRecord> findBySeverity(String severity);

    List<ComplianceRecord> findByTitleContainingIgnoreCase(String keyword);
}