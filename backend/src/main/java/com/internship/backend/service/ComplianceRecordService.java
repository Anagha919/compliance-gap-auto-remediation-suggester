package com.internship.backend.service;

import com.internship.backend.entity.ComplianceRecord;
import com.internship.backend.exception.BadRequestException;
import com.internship.backend.exception.ResourceNotFoundException;
import com.internship.backend.repository.ComplianceRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplianceRecordService {

    private final ComplianceRecordRepository repository;

    public ComplianceRecordService(ComplianceRecordRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public ComplianceRecord createRecord(ComplianceRecord record) {
        validate(record);
        return repository.save(record);
    }

    // READ - ALL
    public List<ComplianceRecord> getAllRecords() {
        return repository.findAll();
    }

    // READ - BY ID
    public ComplianceRecord getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Record not found with id: " + id));
    }

    // FILTER
    public List<ComplianceRecord> getByStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new BadRequestException("Status must not be empty");
        }
        return repository.findByStatus(status);
    }

    // UPDATE
    public ComplianceRecord updateRecord(Long id, ComplianceRecord updated) {
        ComplianceRecord existing = getById(id);

        if (updated.getTitle() != null && !updated.getTitle().isBlank()) {
            existing.setTitle(updated.getTitle());
        }
        if (updated.getDescription() != null) {
            existing.setDescription(updated.getDescription());
        }
        if (updated.getStatus() != null) {
            existing.setStatus(updated.getStatus());
        }
        if (updated.getSeverity() != null) {
            existing.setSeverity(updated.getSeverity());
        }

        return repository.save(existing);
    }

    // DELETE
    public void deleteRecord(Long id) {
        ComplianceRecord record = getById(id);
        repository.delete(record);
    }

    // 🔒 Validation logic (centralized)
    private void validate(ComplianceRecord record) {
        if (record == null) {
            throw new BadRequestException("Request body cannot be null");
        }
        if (record.getTitle() == null || record.getTitle().isBlank()) {
            throw new BadRequestException("Title is required");
        }
        if (record.getStatus() == null || record.getStatus().isBlank()) {
            throw new BadRequestException("Status is required");
        }
        if (record.getSeverity() == null || record.getSeverity().isBlank()) {
            throw new BadRequestException("Severity is required");
        }
    }
}