package com.hotel_management.service;

import com.hotel_management.entity.AppUser;
import com.hotel_management.entity.Property;
import com.hotel_management.entity.Review;
import com.hotel_management.repository.PropertyRepository;
import com.hotel_management.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PropertyRepository propertyRepository;

    public ReviewService(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    public ResponseEntity<Review> writeReview(Review review, long propertyId, AppUser user) {
        Property property = propertyRepository.findById(propertyId).get();
        review.setProperty(property);
        review.setAppUser(user);
        Review savedReview = reviewRepository.save(review);
        return new ResponseEntity<>(savedReview, HttpStatus.OK);

    }
}
