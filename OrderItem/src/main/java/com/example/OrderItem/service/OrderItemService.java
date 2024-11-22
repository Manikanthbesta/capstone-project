package com.example.OrderItem.service;

import com.example.OrderItem.dto.OrderItemDTO;
import com.example.OrderItem.exception.OrderItemNotFoundException;
import com.example.OrderItem.model.OrderItem;
import com.example.OrderItem.repository.OrderItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
@Slf4j
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public Mono<OrderItem> createOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = mapToOrderItem(orderItemDTO);
        return orderItemRepository.save(orderItem)
                .doOnSuccess(item -> log.info("Created order item with ID: {}", item.getId()))
                .doOnError(e -> log.error("Error creating order item: {}", e.getMessage()));
    }

    public Flux<OrderItem> createOrderItems(List<OrderItemDTO> orderItemDTOs) {
        return Flux.fromIterable(orderItemDTOs)
                .flatMap(this::createOrderItem)
                .doOnComplete(() -> log.info("Batch order items creation completed"))
                .doOnError(e -> log.error("Error in batch order items creation: {}", e.getMessage()));
    }

    public Mono<OrderItem> getOrderItem(String id) {
        return orderItemRepository.findById(id)
                .switchIfEmpty(Mono.error(new OrderItemNotFoundException(id)))
                .doOnError(e -> log.error("Error fetching order item {}: {}", id, e.getMessage()));
    }

    public Flux<OrderItem> getOrderItemsByOrderId(String orderId) {
        return orderItemRepository.findByOrderId(orderId)
                .doOnComplete(() -> log.info("Fetched items for order: {}", orderId))
                .doOnError(e -> log.error("Error fetching items for order {}: {}", orderId, e.getMessage()));
    }

    public Flux<OrderItem> getOrderItemsByVendorId(String vendorId) {  // Changed from Integer
        return orderItemRepository.findByVendorId(vendorId)
                .doOnComplete(() -> log.info("Fetched items for vendor: {}", vendorId))
                .doOnError(e -> log.error("Error fetching items for vendor {}: {}", vendorId, e.getMessage()));
    }

    public Mono<OrderItem> updateOrderItem(String id, OrderItemDTO orderItemDTO) {
        return orderItemRepository.findById(id)
                .flatMap(existingItem -> {
                    updateOrderItemFromDTO(existingItem, orderItemDTO);
                    return orderItemRepository.save(existingItem);
                })
                .switchIfEmpty(Mono.error(new OrderItemNotFoundException(id)))
                .doOnSuccess(item -> log.info("Updated order item: {}", id))
                .doOnError(e -> log.error("Error updating order item {}: {}", id, e.getMessage()));
    }

    public Mono<Void> deleteOrderItem(String id) {
        return orderItemRepository.deleteById(id)
                .doOnSuccess(v -> log.info("Deleted order item: {}", id))
                .doOnError(e -> log.error("Error deleting order item {}: {}", id, e.getMessage()));
    }

    public Mono<Void> deleteOrderItemsByOrderId(String orderId) {
        return orderItemRepository.deleteByOrderId(orderId)
                .doOnSuccess(v -> log.info("Deleted items for order: {}", orderId))
                .doOnError(e -> log.error("Error deleting items for order {}: {}", orderId, e.getMessage()));
    }

    private OrderItem mapToOrderItem(OrderItemDTO dto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(dto.getOrderId());
        orderItem.setProductId(dto.getProductId());
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setPrice(dto.getPrice());
        orderItem.setVendorId(dto.getVendorId());
        return orderItem;
    }

    private void updateOrderItemFromDTO(OrderItem orderItem, OrderItemDTO dto) {
        orderItem.setProductId(dto.getProductId());
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setPrice(dto.getPrice());
        orderItem.setVendorId(dto.getVendorId());
    }
}