package com.hotel_management.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendEmail(String to, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true);  // Enable HTML content
            helper.setFrom("abhaysinghanx@gmail.com"); // Replace with your email address (or use dynamic configuration)
            mailSender.send(mimeMessage);
            return "Email Sent Successfully!";
        } catch (MessagingException e) {
            return "Error sending email: " + e.getMessage();
        }
    }
}
