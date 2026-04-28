package com.internship.backend.controller;

import com.internship.backend.entity.ComplianceRecord;
import com.internship.backend.service.ComplianceRecordService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/records")
public class ComplianceRecordController {

    private final ComplianceRecordService service;

    public ComplianceRecordController(ComplianceRecordService service) {
        this.service = service;
    }

    // ✅ GET ALL (PAGINATED)
    @GetMapping("/all")
    public ResponseEntity<Page<ComplianceRecord>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAllPaginated(pageable));
    }

    // ✅ GET BY ID (404 handled in service)
    @GetMapping("/{id}")
    public ResponseEntity<ComplianceRecord> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // ✅ CREATE (VALIDATION)
    @PostMapping("/create")
    public ResponseEntity<ComplianceRecord> create(@Valid @RequestBody ComplianceRecord record) {
        ComplianceRecord saved = service.createRecord(record);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}