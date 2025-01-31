package com.hotel_management.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.phone.number}") // Your Twilio number
    private String fromPhoneNumber;

    public String sendSms(String to, String messageBody) {
        String formattedTo = formatPhoneNumber(to);

        // Send SMS via Twilio
        Message message = Message.creator(
                new PhoneNumber(formattedTo),
                new PhoneNumber(fromPhoneNumber),
                messageBody
        ).create();

        return message.getSid();  // Return Twilio Message ID
    }

    // Format the phone number correctly (E.164 format)
    private String formatPhoneNumber(String to) {
        to = to.trim(); // Remove extra spaces

        if (!to.startsWith("+")) {
            to = "+91" + to;  // Auto-add country code if missing (for India)
        }

        if (!to.matches("^\\+\\d{10,15}$")) {
            throw new IllegalArgumentException("Invalid phone number format: " + to);
        }

        return to;
    }
}
