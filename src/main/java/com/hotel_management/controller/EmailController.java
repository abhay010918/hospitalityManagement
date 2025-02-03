package com.hotel_management.controller;

import com.hotel_management.payload.EmailRequest;
import com.hotel_management.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        String response = emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
        return ResponseEntity.ok(response);
    }
}
