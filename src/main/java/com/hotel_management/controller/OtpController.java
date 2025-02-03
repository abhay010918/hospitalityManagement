package com.hotel_management.controller;

import com.hotel_management.service.OtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> generateOtp(@RequestParam String key) {
        String otp = otpService.generateOtp(key);
        Map<String, String> response = new HashMap<>();
        response.put("message", "OTP generated successfully.");
        response.put("otp", otp);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyOtp(@RequestParam String key, @RequestParam String otpCode) {
        boolean isValid = otpService.verifyOtp(key, otpCode);
        Map<String, String> response = new HashMap<>();
        response.put("message", isValid ? "OTP verified successfully!" : "Invalid or expired OTP.");
        return ResponseEntity.ok(response);
    }
}
