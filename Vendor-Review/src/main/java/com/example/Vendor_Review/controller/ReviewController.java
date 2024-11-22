package com.example.Vendor_Review.controller;



import com.example.Vendor_Review.model.Review;
import com.example.Vendor_Review.dto.ReviewDTO;
import com.example.Vendor_Review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/reviews")
@CrossOrigin
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public Mono<ResponseEntity<Review>> createReview(@RequestBody Review review) {
        return reviewService.createReview(review)
                .map(createdReview -> ResponseEntity.status(201).body(createdReview));
    }

    @GetMapping("/{reviewid}")
    public Mono<ResponseEntity<Review>> getReviewById(@PathVariable String reviewid) {
        return reviewService.getReviewById(reviewid)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/vendor/{vendorId}")
    public Flux<Review> getReviewsByVendorId(@PathVariable String vendorId) {
        return reviewService.getReviewsByVendorId(vendorId);
    }

    @PutMapping("/{reviewid}")
    public Mono<ResponseEntity<Review>> updateReview(@PathVariable String reviewid, @RequestBody ReviewDTO reviewDTO) {
        return reviewService.updateReview(reviewid, reviewDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{reviewid}")
    public Mono<Void> deleteReview(@PathVariable String reviewid) {
        return reviewService.deleteReview(reviewid);
    }


}
