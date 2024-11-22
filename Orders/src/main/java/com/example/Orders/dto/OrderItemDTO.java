package com.example.Orders.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private String id;
    private String productId;
    private Integer quantity;
    private BigDecimal price;
    private String vendorId;
}