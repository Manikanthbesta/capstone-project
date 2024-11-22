package com.example.Vendor.dto;
import lombok.Data;

@Data
public class ReviewDTO {
    private String customerId;
    private Integer rating;
    private String comment;
}
