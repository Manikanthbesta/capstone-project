package com.example.OrderItem.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

@Data
@Document(collection = "order_items")
public class OrderItem {
    @Id
    private String id;
    private String orderId;
    private String productId;  // Changed from Integer
    private Integer quantity;
    private BigDecimal price;
    private String vendorId;   // Changed from Integer
}