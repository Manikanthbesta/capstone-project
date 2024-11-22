package com.example.ProductReview.repository;

import com.example.ProductReview.model.ProductReview;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProductReviewRepository extends ReactiveMongoRepository<ProductReview, String> {
    Flux<ProductReview> findByProductId(String productId); // Find reviews by product ID
}
