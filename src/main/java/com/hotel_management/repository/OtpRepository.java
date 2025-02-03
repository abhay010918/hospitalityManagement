package com.hotel_management.repository;

import com.hotel_management.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByOtpKey(String otpKey);
    void deleteByOtpKey(String otpKey);
}
