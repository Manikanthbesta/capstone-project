package com.example.Orders.feign;

import com.example.Orders.dto.OrderItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "orderitem-service", url = "${orderitem-service.url}")
public interface OrderItemClient {
    @GetMapping("/api/orderitems/{orderId}")
    List<OrderItemDTO> getOrderItemsByOrderId(@PathVariable("orderId") String orderId);

    @PostMapping("/api/orderitems")
    List<OrderItemDTO> createOrderItems(@RequestBody List<OrderItemDTO> items);

    @DeleteMapping("/api/orderitems/{orderId}")
    void deleteOrderItems(@PathVariable("orderId") String orderId);

    @PutMapping("/api/orderitems/{orderId}")
    List<OrderItemDTO> updateOrderItems(
            @PathVariable("orderId") String orderId,
            @RequestBody List<OrderItemDTO> items
    );
}