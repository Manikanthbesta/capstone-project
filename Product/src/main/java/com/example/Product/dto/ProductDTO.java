package com.example.Product.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDTO {
    private String id;
    private String productName;
    private String description;
    private BigDecimal price;
    private String category;
    private String productImageUrl;
    private VendorDTO vendor;  // This is a reference to the VendorDTO class

    // Nested VendorDTO class
    @Data
    @NoArgsConstructor
    public static class VendorDTO {
        private String id;
        private String name;
        private String contactEmail;
        private String location;
        private String contactPhoneNumber;
        private String address;
        private String businessCategory;
        private String storeName;
        private String profileImageUrl;
    }
}
