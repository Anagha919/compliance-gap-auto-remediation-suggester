package com.internship.backend.config;

import com.internship.backend.entity.ComplianceRecord;
import com.internship.backend.repository.ComplianceRecordRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Random;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(ComplianceRecordRepository repository) {
        return args -> {

            if (repository.count() > 0) return; // avoid duplicate seeding

            String[] statuses = {"OPEN", "CLOSED"};
            String[] severities = {"LOW", "MEDIUM", "HIGH"};

            Random random = new Random();

            for (int i = 1; i <= 30; i++) {

                ComplianceRecord record = ComplianceRecord.builder()
                        .title("Compliance Task " + i)
                        .description("Auto-generated record " + i)
                        .status(statuses[random.nextInt(statuses.length)])
                        .severity(severities[random.nextInt(severities.length)])
                        .deadline(LocalDate.now().plusDays(random.nextInt(10)))
                        .createdBy("system")
                        .build();

                repository.save(record);
            }

            System.out.println("🔥 30 records seeded successfully!");
        };
    }
}