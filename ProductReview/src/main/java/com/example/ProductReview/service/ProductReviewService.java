package com.example.ProductReview.service;

import com.example.ProductReview.model.ProductReview;
import com.example.ProductReview.repository.ProductReviewRepository;
import com.example.ProductReview.feign.ProductFeignClient;
import com.example.ProductReview.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Service
public class ProductReviewService {

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private ProductFeignClient productFeignClient;

    // Create a new review for a product
    public Mono<ProductReview> createReview(ProductReview productReview) {
        return productReviewRepository.save(productReview);
    }

    // Get all reviews for a specific product by product ID
    public Flux<ProductReview> getReviewsByProductId(String productId) {
        return productReviewRepository.findByProductId(productId);
    }

    // Get product details by product ID using Feign client
    public Mono<ProductDTO> getProductById(String productId) {
        return Mono.fromCallable(() -> productFeignClient.getProductById(productId));
    }

    // Update an existing review
    public Mono<ProductReview> updateReview(String id, ProductReview productReview) {
        return productReviewRepository.findById(id)
                .flatMap(existingReview -> {
                    existingReview.setRating(productReview.getRating());
                    existingReview.setComment(productReview.getComment());
                    return productReviewRepository.save(existingReview);
                });
    }

    // Delete a review by ID
    public Mono<Void> deleteReview(String id) {
        return productReviewRepository.deleteById(id);
    }
}
