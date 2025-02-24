package com.hotel_management.repository;

import com.hotel_management.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser>findByUsername(String username);
    Optional<AppUser> findByEmail(String email);

}