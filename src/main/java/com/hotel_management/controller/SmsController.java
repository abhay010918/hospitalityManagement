package com.hotel_management.controller;

import com.hotel_management.service.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendSms(
            @RequestParam String to,
            @RequestParam String message) {
        String messageId = smsService.sendSms(to, message);
        return ResponseEntity.ok("SMS Sent! Message ID: " + messageId);
    }
}
