package com.example.OrderItem.repository;

import com.example.OrderItem.model.OrderItem;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OrderItemRepository extends ReactiveMongoRepository<OrderItem, String> {
    Flux<OrderItem> findByOrderId(String orderId);
    Mono<Void> deleteByOrderId(String orderId);
    Flux<OrderItem> findByVendorId(String vendorId);    // Changed from Integer
    Flux<OrderItem> findByProductId(String productId);  // Changed from Integer
}