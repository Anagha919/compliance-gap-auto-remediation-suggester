package com.internship.backend.service;

import com.internship.backend.entity.ComplianceRecord;
import com.internship.backend.exception.BadRequestException;
import com.internship.backend.exception.ResourceNotFoundException;
import com.internship.backend.repository.ComplianceRecordRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ComplianceRecordService {

    private final ComplianceRecordRepository repository;

    public ComplianceRecordService(ComplianceRecordRepository repository) {
        this.repository = repository;
    }

    // 🔥 GET ALL WITH PAGINATION (CACHED)
    @Cacheable(value = "records", key = "#pageable.pageNumber")
    public Page<ComplianceRecord> getAllPaginated(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // 🔥 GET BY ID (CACHED)
    @Cacheable(value = "record", key = "#id")
    public ComplianceRecord getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + id));
    }

    // 🔥 CREATE (CLEAR CACHE)
    @CacheEvict(value = {"records", "record"}, allEntries = true)
    public ComplianceRecord createRecord(ComplianceRecord record) {
        validate(record);
        return repository.save(record);
    }

    // 🔥 UPDATE (CLEAR CACHE)
    @CacheEvict(value = {"records", "record"}, allEntries = true)
    public ComplianceRecord updateRecord(Long id, ComplianceRecord updated) {

        ComplianceRecord existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + id));

        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setStatus(updated.getStatus());
        existing.setSeverity(updated.getSeverity());

        validate(existing);

        return repository.save(existing);
    }

    // 🔥 DELETE (CLEAR CACHE)
    @CacheEvict(value = {"records", "record"}, allEntries = true)
    public void deleteRecord(Long id) {
        ComplianceRecord existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found with id: " + id));

        repository.delete(existing);
    }

    // 🔥 VALIDATION
    private void validate(ComplianceRecord record) {
        if (record.getTitle() == null || record.getTitle().isBlank()) {
            throw new BadRequestException("Title cannot be empty");
        }

        if (record.getStatus() == null || record.getStatus().isBlank()) {
            throw new BadRequestException("Status cannot be empty");
        }

        if (record.getSeverity() == null || record.getSeverity().isBlank()) {
            throw new BadRequestException("Severity cannot be empty");
        }
    }
}