package com.example.Orders.controller;


import com.example.Orders.dto.OrderDTO;
import com.example.Orders.model.Order;
import com.example.Orders.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
//
//@RestController
//@RequestMapping("/orders")
//@CrossOrigin
//@Slf4j
//public class OrderController {
//    private final OrderService orderService;
//
//    @Autowired
//    public OrderController(OrderService orderService) {
//        this.orderService = orderService;
//    }
//
//    @PostMapping
//    public Mono<ResponseEntity<Order>> createOrder(@RequestBody OrderDTO orderDTO) {
//        log.info("Received request to create order for customer: {}", orderDTO.getCustomerId());
//        return orderService.createOrder(orderDTO)
//                .map(order -> ResponseEntity.status(HttpStatus.CREATED).body(order))
//                .doOnSuccess(response -> log.info("Order created successfully"))
//                .doOnError(e -> log.error("Error creating order: {}", e.getMessage()));
//    }
//
//    @GetMapping("/{orderId}")
//    public Mono<ResponseEntity<Order>> getOrder(@PathVariable String orderId) {
//        log.info("Fetching order with ID: {}", orderId);
//        return orderService.getOrder(orderId)
//                .map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.notFound().build())
//                .doOnError(e -> log.error("Error fetching order {}: {}", orderId, e.getMessage()));
//    }
//
//    @GetMapping("/enriched/{orderId}")
//    public Mono<ResponseEntity<EnrichedOrderDTO>> getEnrichedOrder(@PathVariable String orderId) {
//        log.info("Fetching enriched order with ID: {}", orderId);
//        return orderService.getEnrichedOrder(orderId)
//                .map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.notFound().build())
//                .doOnError(e -> log.error("Error fetching enriched order {}: {}", orderId, e.getMessage()));
//    }
//
//    @PutMapping("/{orderId}")
//    public Mono<ResponseEntity<Order>> updateOrder(
//            @PathVariable String orderId,
//            @RequestBody OrderDTO orderDTO) {
//        log.info("Updating order with ID: {}", orderId);
//        return orderService.updateOrder(orderId, orderDTO)
//                .map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.notFound().build())
//                .doOnError(e -> log.error("Error updating order {}: {}", orderId, e.getMessage()));
//    }
//
//    @DeleteMapping("/{orderId}")
//    public Mono<ResponseEntity<Void>> deleteOrder(@PathVariable String orderId) {
//        log.info("Deleting order with ID: {}", orderId);
//        return orderService.deleteOrder(orderId)
//                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
//                .defaultIfEmpty(ResponseEntity.notFound().build())
//                .doOnError(e -> log.error("Error deleting order {}: {}", orderId, e.getMessage()));
//    }
//
//    @GetMapping("/customer/{customerId}")
//    public Flux<Order> getCustomerOrders(@PathVariable String customerId) {
//        log.info("Fetching orders for customer: {}", customerId);
//        return orderService.getOrdersByCustomerId(customerId)
//                .doOnComplete(() -> log.info("Successfully fetched orders for customer: {}", customerId))
//                .doOnError(e -> log.error("Error fetching orders for customer {}: {}", customerId, e.getMessage()));
//    }
//
//    @DeleteMapping("/customer/{customerId}")
//    public Mono<ResponseEntity<Void>> deleteCustomerOrders(@PathVariable String customerId) {
//        log.info("Deleting all orders for customer: {}", customerId);
//        return orderService.deleteCustomerOrders(customerId)
//                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
//                .doOnSuccess(response -> log.info("Successfully deleted orders for customer: {}", customerId))
//                .doOnError(e -> log.error("Error deleting orders for customer {}: {}", customerId, e.getMessage()));
//    }
//
//    // Exception Handlers
//    @ExceptionHandler(OrderNotFoundException.class)
//    public ResponseEntity<String> handleOrderNotFound(OrderNotFoundException ex) {
//        log.error("Order not found: {}", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGenericException(Exception ex) {
//        log.error("Unexpected error: {}", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("An unexpected error occurred");
//    }
//}

@RestController
@RequestMapping("/orders")
@Slf4j
@CrossOrigin(origins = "http://localhost:3002")
public class OrderController {

    @Autowired
   public OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            Order order = orderService.createOrder(orderDTO);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating order: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
        log.info("Fetching order: {}", orderId);
        Order order = orderService.getOrder(orderId);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/details/{orderId}")
    public ResponseEntity<OrderDTO> getOrderWithDetails(@PathVariable String orderId) {
        OrderDTO orderDTO = orderService.getOrderWithDetails(orderId);
        if (orderDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable String orderId, @RequestBody OrderDTO orderDTO) {
        log.info("Updating order: {}", orderId);
        Order updatedOrder = orderService.updateOrder(orderId, orderDTO);
        if (updatedOrder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        log.info("Deleting order: {}", orderId);
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getCustomerOrders(@PathVariable String customerId) {
        log.info("Fetching orders for customer: {}", customerId);
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Void> deleteCustomerOrders(@PathVariable String customerId) {
        log.info("Deleting orders for customer: {}", customerId);
        orderService.deleteCustomerOrders(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}