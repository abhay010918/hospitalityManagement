package com.hotel_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;


@Entity
@Getter
@Setter
@Table(name = "otp_store")
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "otp_key", nullable = false, unique = true)
    private String otpKey;  // Renamed from 'key' to 'otpKey'

    @Column(nullable = false)
    private String otpCode;

    @Column(nullable = false)
    private ZonedDateTime expiryTime;

    public Otp() {
    }

    public Otp(String otpKey, String otpCode, ZonedDateTime expiryTime) {
        this.otpKey = otpKey;
        this.otpCode = otpCode;
        this.expiryTime = expiryTime;
    }

}
