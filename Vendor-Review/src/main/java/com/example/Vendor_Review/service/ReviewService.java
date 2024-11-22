package com.example.Vendor_Review.service;



import com.example.Vendor_Review.model.Review;
import com.example.Vendor_Review.repository.ReviewRepository;
import com.example.Vendor_Review.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;



    public Mono<Review> createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Flux<Review> getReviewsByVendorId(String vendorId) {
        return reviewRepository.findByVendorId(vendorId);
    }

    public Mono<Review> getReviewById(String id) {
        return reviewRepository.findById(id);
    }

    public Mono<Review> updateReview(String id, ReviewDTO reviewDTO) {
        return reviewRepository.findById(id)
                .flatMap(existingReview -> {
                    existingReview.setCustomerId(reviewDTO.getCustomerId());
                    existingReview.setRating(reviewDTO.getRating());
                    existingReview.setComment(reviewDTO.getComment());
                    return reviewRepository.save(existingReview);
                });
    }

    public Mono<Void> deleteReview(String id) {
        return reviewRepository.deleteById(id);
    }


}

