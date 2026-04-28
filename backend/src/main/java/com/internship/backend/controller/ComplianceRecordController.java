package com.internship.backend.controller;

import com.internship.backend.entity.ComplianceRecord;
import com.internship.backend.service.ComplianceRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class ComplianceRecordController {

    private final ComplianceRecordService service;

    public ComplianceRecordController(ComplianceRecordService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public ComplianceRecord create(@RequestBody ComplianceRecord record) {
        return service.createRecord(record);
    }

    // GET ALL
    @GetMapping
    public List<ComplianceRecord> getAll() {
        return service.getAllRecords();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ComplianceRecord getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // FILTER BY STATUS
    @GetMapping("/status/{status}")
    public List<ComplianceRecord> getByStatus(@PathVariable String status) {
        return service.getByStatus(status);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ComplianceRecord update(@PathVariable Long id,
                                   @RequestBody ComplianceRecord record) {
        return service.updateRecord(id, record);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteRecord(id);
        return "Record deleted successfully";
    }
}