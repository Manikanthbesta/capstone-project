package com.example.ProductReview.controller;

import com.example.ProductReview.model.ProductReview;
import com.example.ProductReview.service.ProductReviewService;
import com.example.ProductReview.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/product-reviews")
@CrossOrigin
public class ProductReviewController {

    @Autowired
    private ProductReviewService productReviewService;

    // Create a new product review
    @PostMapping
    public Mono<ResponseEntity<ProductReview>> createReview(@RequestBody ProductReview productReview) {
        return productReviewService.createReview(productReview)
                .map(createdReview -> ResponseEntity.ok(createdReview));
    }

    // Get all reviews for a specific product
    @GetMapping("/product/{productId}")
    public Flux<ProductReview> getReviewsByProductId(@PathVariable String productId) {
        return productReviewService.getReviewsByProductId(productId);
    }

    // Get product details along with reviews
    @GetMapping("/product/{productId}/with-reviews")
    public Mono<ResponseEntity<ProductDTO>> getProductWithReviews(@PathVariable String productId) {
        return productReviewService.getProductById(productId)
                .map(productDTO -> ResponseEntity.ok(productDTO))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Update an existing product review
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductReview>> updateReview(@PathVariable String id, @RequestBody ProductReview productReview) {
        return productReviewService.updateReview(id, productReview)
                .map(updatedReview -> ResponseEntity.ok(updatedReview))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Delete a review by ID
    @DeleteMapping("/{id}")
    public Mono<Void> deleteReview(@PathVariable String id) {
        return productReviewService.deleteReview(id);
    }
}
