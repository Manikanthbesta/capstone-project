package com.example.Vendor_Review.repository;


import com.example.Vendor_Review.model.Review;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ReviewRepository extends ReactiveMongoRepository<Review, String> {
    Flux<Review> findByVendorId(String vendorId); // Find reviews by vendorId
}

