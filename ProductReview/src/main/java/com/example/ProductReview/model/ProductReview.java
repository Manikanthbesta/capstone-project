package com.example.ProductReview.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "product_reviews")
public class ProductReview {
    @Id
    private String id; // MongoDB ObjectId
    private String productId;  // Link to Product by productId
    private String customerId; // Customer who gave the review
    private Integer rating;     // Rating given by the customer
    private String comment;     // Review comment
}
