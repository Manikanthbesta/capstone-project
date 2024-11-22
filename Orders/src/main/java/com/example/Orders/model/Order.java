package com.example.Orders.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String customerId;
    private LocalDate orderDate;
    private Double totalAmount;
    private String shippingAddress;
    private List<String> orderItemIds;
}