package com.example.Orders.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO {
    private String id;
    private String customerId;
    private LocalDate orderDate;
    private Double totalAmount;
    private String shippingAddress;
    private List<OrderItemDTO> items;
}