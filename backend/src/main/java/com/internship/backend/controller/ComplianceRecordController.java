package com.internship.backend.controller;

import com.internship.backend.entity.ComplianceRecord;
import com.internship.backend.exception.BadRequestException;
import com.internship.backend.service.ComplianceRecordService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/records")
public class ComplianceRecordController {

    private final ComplianceRecordService service;

    public ComplianceRecordController(ComplianceRecordService service) {
        this.service = service;
    }

    // 🔥 GET ALL (pagination + defaults + safe check)
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Page<ComplianceRecord>> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (page < 0 || size <= 0) {
            throw new BadRequestException("Invalid pagination values");
        }

        Page<ComplianceRecord> result =
                service.getAllPaginated(PageRequest.of(page, size));

        return ResponseEntity.ok(result);
    }

    // 🔥 GET BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ComplianceRecord> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // 🔥 CREATE
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ComplianceRecord> create(
            @Valid @RequestBody ComplianceRecord record) {

        ComplianceRecord created = service.createRecord(record);
        return ResponseEntity.status(201).body(created);
    }

    // 🔥 UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ComplianceRecord> update(
            @PathVariable Long id,
            @Valid @RequestBody ComplianceRecord record) {

        ComplianceRecord updated = service.updateRecord(id, record);
        return ResponseEntity.ok(updated);
    }

    // 🔥 DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}