package com.example.OrderItem.exception;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(String id) {
        super("Order item not found with id: " + id);
    }
}