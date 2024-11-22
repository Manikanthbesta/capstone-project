package com.example.OrderItem.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private String id;
    private String orderId;
    private String productId;  // Changed from Integer
    private Integer quantity;
    private BigDecimal price;
    private String vendorId;   // Changed from Integer
}