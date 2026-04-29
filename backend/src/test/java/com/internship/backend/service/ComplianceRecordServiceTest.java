package com.internship.backend.service;

import com.internship.backend.entity.ComplianceRecord;
import com.internship.backend.exception.ResourceNotFoundException;
import com.internship.backend.repository.ComplianceRecordRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ComplianceRecordServiceTest {

    @Mock
    private ComplianceRecordRepository repository;

    @InjectMocks
    private ComplianceRecordService service;

    @Test
    void testGetByIdSuccess() {
        ComplianceRecord record = new ComplianceRecord();
        record.setId(1L);
        record.setTitle("Test");

        when(repository.findById(1L)).thenReturn(Optional.of(record));

        ComplianceRecord result = service.getById(1L);

        assertNotNull(result);
        assertEquals("Test", result.getTitle());
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getById(1L));
    }

    @Test
    void testCreateRecord() {
        ComplianceRecord record = new ComplianceRecord();
        record.setTitle("Test");
        record.setStatus("OPEN");
        record.setSeverity("HIGH");

        when(repository.save(record)).thenReturn(record);

        ComplianceRecord result = service.createRecord(record);

        assertNotNull(result);
        verify(repository, times(1)).save(record);
    }

    @Test
    void testDeleteRecord() {
        ComplianceRecord record = new ComplianceRecord();
        record.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(record));

        service.deleteRecord(1L);

        verify(repository, times(1)).delete(record);
    }
}