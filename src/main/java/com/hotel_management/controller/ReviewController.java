package com.hotel_management.controller;

import com.hotel_management.entity.AppUser;
import com.hotel_management.entity.Review;
import com.hotel_management.repository.PropertyRepository;
import com.hotel_management.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService, PropertyRepository propertyRepository) {
        this.reviewService = reviewService;
    }

    @PostMapping("/writeReview")
    public ResponseEntity<Review> addReview(
            @RequestBody Review review,
            @RequestParam long propertyId,
            @AuthenticationPrincipal AppUser user
            ){
        return reviewService.writeReview(review,propertyId,user);
    }
}
