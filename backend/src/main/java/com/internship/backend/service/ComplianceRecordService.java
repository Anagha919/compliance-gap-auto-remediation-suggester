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

    // 🔥 GET ALL (Paginated + Cached)
    @Cacheable(value = "records",
            key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<ComplianceRecord> getAllPaginated(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // 🔥 GET BY ID (Cached)
    @Cacheable(value = "record", key = "#id")
    public ComplianceRecord getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Record not found with id: " + id));
    }

    // 🔥 CREATE
    @CacheEvict(value = {"records"}, allEntries = true)
    public ComplianceRecord createRecord(ComplianceRecord record) {

        if (record.getTitle() == null || record.getTitle().isBlank()) {
            throw new BadRequestException("Title cannot be empty");
        }

        return repository.save(record);
    }

    // 🔥 UPDATE
    @CacheEvict(value = {"records", "record"}, allEntries = true)
    public ComplianceRecord updateRecord(Long id, ComplianceRecord updatedRecord) {

        ComplianceRecord existing = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Record not found with id: " + id));

        existing.setTitle(updatedRecord.getTitle());
        existing.setDescription(updatedRecord.getDescription());
        existing.setStatus(updatedRecord.getStatus());
        existing.setSeverity(updatedRecord.getSeverity());
        existing.setDeadline(updatedRecord.getDeadline());

        return repository.save(existing);
    }

    // 🔥 DELETE
    @CacheEvict(value = {"records", "record"}, allEntries = true)
    public void deleteRecord(Long id) {

        ComplianceRecord existing = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Record not found with id: " + id));

        repository.delete(existing);
    }
}