package com.hotel_management.service;

import com.hotel_management.entity.AppUser;
import com.hotel_management.entity.Property;
import com.hotel_management.entity.Review;
import com.hotel_management.repository.PropertyRepository;
import com.hotel_management.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PropertyRepository propertyRepository;

    public ReviewService(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    // For Writing The Review
    public ResponseEntity<?> writeReview(Review review, long propertyId, AppUser user) {
        Property property = propertyRepository.findById(propertyId).get();
        if(reviewRepository.existsByAppUserAndProperty(user,property)){
            return new ResponseEntity<>("User Already Exists", HttpStatus.NOT_FOUND);
        }
        review.setProperty(property);
        review.setAppUser(user);
        Review savedReview = reviewRepository.save(review);
        return new ResponseEntity<>(savedReview, HttpStatus.OK);
    }

    // For reading the review
    public ResponseEntity<List<Review>> getReview(AppUser user){
        List<Review> byAppUser = reviewRepository.findByAppUser(user);
        return new ResponseEntity<>(byAppUser, HttpStatus.OK);

    }
}
