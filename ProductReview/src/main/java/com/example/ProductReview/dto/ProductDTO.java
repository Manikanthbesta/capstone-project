package com.example.ProductReview.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private String id;
    private String productName;
    private String description;
    private String category;
    private String productImageUrl;
}
