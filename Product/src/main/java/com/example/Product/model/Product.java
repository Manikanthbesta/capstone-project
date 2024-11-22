package com.example.Product.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String vendorId;
    private String productName;
    private String description;
    private BigDecimal price;
    private String category;
    private String productImageUrl;

}