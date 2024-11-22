package com.example.Vendor_Review.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "reviews")
public class Review {
    @Id
    private String reviewid;
    private String vendorId;
    private String customerId;
    private Integer rating;
    private String comment;
}
