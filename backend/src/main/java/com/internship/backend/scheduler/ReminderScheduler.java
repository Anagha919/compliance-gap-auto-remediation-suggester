package com.internship.backend.scheduler;

import com.internship.backend.entity.ComplianceRecord;
import com.internship.backend.repository.ComplianceRecordRepository;
import com.internship.backend.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReminderScheduler {

    private final ComplianceRecordRepository repository;
    private final EmailService emailService;

    public ReminderScheduler(ComplianceRecordRepository repository,
                             EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    // 🔥 Runs every 1 minute (for testing)
    @Scheduled(cron = "0 */1 * * * ?")
    public void sendReminders() {

        List<ComplianceRecord> records = repository.findAll();

        for (ComplianceRecord record : records) {

            // ✅ Reminder for OPEN tasks
            if ("OPEN".equalsIgnoreCase(record.getStatus())) {
                emailService.sendEmail(
                        "your_email@gmail.com",
                        "Reminder",
                        "<h3>Pending: " + record.getTitle() + "</h3>"
                );
            }

            // 🔥 Deadline alert
            if (record.getDeadline() != null &&
                record.getDeadline().isBefore(LocalDate.now().plusDays(1))) {

                emailService.sendEmail(
                        "your_email@gmail.com",
                        "Deadline Alert ⚠️",
                        "<h3>Due soon: " + record.getTitle() + "</h3>"
                );
            }
        }
    }
}