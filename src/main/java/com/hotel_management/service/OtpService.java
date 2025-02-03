package com.hotel_management.service;

import com.hotel_management.entity.Otp;
import com.hotel_management.repository.OtpRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.Optional;

@Service
public class OtpService {

    private static final int OTP_LENGTH = 6;
    private static final long OTP_EXPIRY_MINUTES = 5;
    private static final SecureRandom random = new SecureRandom();

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    private final OtpRepository otpRepository;

    public OtpService(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public String generateOtp(String otpKey) {
        // Ensure phone number has '+'
        if (!otpKey.startsWith("+")) {
            otpKey = "+" + otpKey;
        }

        // Generate a 6-digit OTP
        String otpCode = String.format("%06d", random.nextInt(1000000));
        ZonedDateTime expiryTime = ZonedDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES);

        // Delete old OTP if exists
        otpRepository.deleteByOtpKey(otpKey);

        // Save OTP in the database
        Otp otp = new Otp(otpKey, otpCode, expiryTime);
        otpRepository.save(otp);

        // Send OTP via SMS using Twilio
        sendOtpSms(otpKey, otpCode);

        return otpCode;
    }

    public boolean verifyOtp(String otpKey, String otpCode) {
        Optional<Otp> otpOptional = otpRepository.findByOtpKey(otpKey);

        if (otpOptional.isPresent()) {
            Otp otp = otpOptional.get();

            // Log OTP details
//            System.out.println("Stored OTP: " + otp.getOtpCode());
//            System.out.println("Stored Expiry Time: " + otp.getExpiryTime());
//            System.out.println("Current Time: " + ZonedDateTime.now(ZoneId.of("UTC")));

            // Compare OTP and expiry time
            if (otp.getOtpCode().equals(otpCode) && ZonedDateTime.now(ZoneId.of("UTC")).isBefore(ChronoZonedDateTime.from(otp.getExpiryTime()))) {
                otpRepository.delete(otp); // Remove OTP after successful verification
                return true;
            }
        }

        return false;
    }


    private void sendOtpSms(String to, String otpCode) {
        try {

            Twilio.init(accountSid, authToken);
            System.out.println("Sending OTP to: " + to);

            Message message = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(fromPhoneNumber), // Your Twilio number
                    "Your OTP code is: " + otpCode
            ).create();

            System.out.println("OTP Sent Successfully! Message SID: " + message.getSid());
        } catch (Exception e) {
            System.err.println("Error sending OTP SMS: " + e.getMessage());
        }
    }
}
