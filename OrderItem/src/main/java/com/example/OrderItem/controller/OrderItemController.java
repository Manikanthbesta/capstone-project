package com.example.OrderItem.controller;
import com.example.OrderItem.dto.OrderItemDTO;
import com.example.OrderItem.model.OrderItem;
import com.example.OrderItem.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/orderitems")
@Slf4j
public class OrderItemController {
    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping
    public Mono<ResponseEntity<OrderItem>> createOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
        return orderItemService.createOrderItem(orderItemDTO)
                .map(item -> new ResponseEntity<>(item, HttpStatus.CREATED));
    }

    @PostMapping("/batch")
    public Flux<OrderItem> createOrderItems(@RequestBody List<OrderItemDTO> orderItemDTOs) {
        return orderItemService.createOrderItems(orderItemDTOs);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<OrderItem>> getOrderItem(@PathVariable String id) {
        return orderItemService.getOrderItem(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/order/{orderId}")
    public Flux<OrderItem> getOrderItemsByOrderId(@PathVariable String orderId) {
        return orderItemService.getOrderItemsByOrderId(orderId);
    }

    @GetMapping("/vendor/{vendorId}")
    public Flux<OrderItem> getOrderItemsByVendorId(@PathVariable String vendorId) {
        return orderItemService.getOrderItemsByVendorId(vendorId);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<OrderItem>> updateOrderItem(
            @PathVariable String id,
            @RequestBody OrderItemDTO orderItemDTO) {
        return orderItemService.updateOrderItem(id, orderItemDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteOrderItem(@PathVariable String id) {
        return orderItemService.deleteOrderItem(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    }

    @DeleteMapping("/order/{orderId}")
    public Mono<ResponseEntity<Void>> deleteOrderItemsByOrderId(@PathVariable String orderId) {
        return orderItemService.deleteOrderItemsByOrderId(orderId)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    }
}