package com.internship.backend.controller;

import com.internship.backend.service.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final EmailService emailService;

    public TestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/test-email")
    public String testEmail() {
        emailService.sendEmail(
                "your_email@gmail.com",   // 🔥 replace with your email
                "Test Email",
                "<h2>Email working successfully 🚀</h2>"
        );
        return "Email sent!";
    }
}