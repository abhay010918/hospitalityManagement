package com.hotel_management.repository;

import com.hotel_management.entity.AppUser;
import com.hotel_management.entity.Property;
import com.hotel_management.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByAppUser(AppUser user);

    boolean existsByAppUserAndProperty(AppUser user, Property property);
}