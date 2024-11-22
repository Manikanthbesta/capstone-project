package com.example.Orders.service;

import com.example.Orders.dto.*;
import com.example.Orders.feign.CustomerClient;
import com.example.Orders.feign.OrderItemClient;
import com.example.Orders.model.Order;
import com.example.Orders.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class OrderService {
//    private final OrderRepository orderRepository;
//    private final OrderItemClient orderItemClient;
//    private final CustomerClient customerClient;
//
//    @Autowired
//    public OrderService(OrderRepository orderRepository, OrderItemClient orderItemClient, CustomerClient customerClient) {
//        this.orderRepository = orderRepository;
//        this.orderItemClient = orderItemClient;
//        this.customerClient = customerClient;
//    }
//
//    public Mono<Order> createOrder(OrderDTO orderDTO) {
//        log.info("Starting order creation process for customer: {}", orderDTO.getCustomerId());
//
//        // First validate customer exists
//        return Mono.fromCallable(() -> {
//                    try {
//                        CustomerDTO customer = customerClient.getCustomer(orderDTO.getCustomerId());
//                        log.info("Customer validated successfully: {}", customer.getId());
//                        return customer;
//                    } catch (FeignException e) {
//                        log.error("Error validating customer: {}", e.getMessage());
//                        throw new RuntimeException("Customer validation failed: " + e.getMessage());
//                    }
//                })
//                .subscribeOn(Schedulers.boundedElastic())
//                .then(Mono.defer(() -> {
//                    // Create the order first
//                    Order order = mapToOrder(orderDTO);
//                    log.info("Mapped OrderDTO to Order entity");
//
//                    return orderRepository.save(order)
//                            .flatMap(savedOrder -> {
//                                log.info("Order saved initially with ID: {}", savedOrder.getId());
//
//                                // Then create order items
//                                return Mono.fromCallable(() -> {
//                                            try {
//                                                List<OrderItemDTO> createdItems = orderItemClient.createOrderItems(orderDTO.getItems());
//                                                log.info("Order items created successfully, count: {}", createdItems.size());
//                                                return createdItems;
//                                            } catch (FeignException e) {
//                                                log.error("Error creating order items: {}", e.getMessage());
//                                                throw new RuntimeException("Failed to create order items: " + e.getMessage());
//                                            }
//                                        })
//                                        .subscribeOn(Schedulers.boundedElastic())
//                                        .flatMap(createdItems -> {
//                                            savedOrder.setOrderItemIds(createdItems.stream()
//                                                    .map(OrderItemDTO::getId)
//                                                    .collect(Collectors.toList()));
//                                            log.info("Updating order with created item IDs");
//                                            return orderRepository.save(savedOrder);
//                                        });
//                            });
//                }))
//                .doOnSuccess(order -> log.info("Order created successfully with ID: {}", order.getId()))
//                .doOnError(e -> log.error("Error in order creation process: {}", e.getMessage()));
//    }
//
//    public Mono<Order> getOrder(String orderId) {
//        log.info("Fetching order with ID: {}", orderId);
//        return orderRepository.findById(orderId)
//                .switchIfEmpty(Mono.error(new OrderNotFoundException(orderId)))
//                .doOnSuccess(order -> log.info("Successfully retrieved order: {}", orderId))
//                .doOnError(e -> log.error("Error retrieving order {}: {}", orderId, e.getMessage()));
//    }
//
//    public Mono<EnrichedOrderDTO> getEnrichedOrder(String orderId) {
//        log.info("Fetching enriched order with ID: {}", orderId);
//        return orderRepository.findById(orderId)
//                .flatMap(this::enrichOrder)
//                .switchIfEmpty(Mono.error(new OrderNotFoundException(orderId)))
//                .doOnSuccess(enrichedOrder -> log.info("Successfully retrieved enriched order: {}", orderId))
//                .doOnError(e -> log.error("Error retrieving enriched order {}: {}", orderId, e.getMessage()));
//    }
//
//    public Mono<Order> updateOrder(String orderId, OrderDTO orderDTO) {
//        log.info("Updating order with ID: {}", orderId);
//        return orderRepository.findById(orderId)
//                .flatMap(existingOrder -> Mono.fromCallable(() -> {
//                            try {
//                                updateOrderFromDTO(existingOrder, orderDTO);
//                                List<OrderItemDTO> updatedItems = orderItemClient.updateOrderItems(orderId, orderDTO.getItems());
//                                existingOrder.setOrderItemIds(updatedItems.stream()
//                                        .map(OrderItemDTO::getId)
//                                        .collect(Collectors.toList()));
//                                return existingOrder;
//                            } catch (FeignException e) {
//                                log.error("Error updating order items: {}", e.getMessage());
//                                throw new RuntimeException("Failed to update order items: " + e.getMessage());
//                            }
//                        })
//                        .subscribeOn(Schedulers.boundedElastic())
//                        .flatMap(orderRepository::save))
//                .switchIfEmpty(Mono.error(new OrderNotFoundException(orderId)))
//                .doOnSuccess(order -> log.info("Successfully updated order: {}", orderId))
//                .doOnError(e -> log.error("Error updating order {}: {}", orderId, e.getMessage()));
//    }
//
//    public Mono<Void> deleteOrder(String orderId) {
//        log.info("Deleting order with ID: {}", orderId);
//        return orderRepository.findById(orderId)
//                .flatMap(order -> Mono.fromRunnable(() -> {
//                            try {
//                                orderItemClient.deleteOrderItems(orderId);
//                                log.info("Successfully deleted order items for order: {}", orderId);
//                            } catch (FeignException e) {
//                                log.error("Error deleting order items: {}", e.getMessage());
//                                throw new RuntimeException("Failed to delete order items: " + e.getMessage());
//                            }
//                        })
//                        .subscribeOn(Schedulers.boundedElastic())
//                        .then(orderRepository.deleteById(orderId)))
//                .switchIfEmpty(Mono.error(new OrderNotFoundException(orderId)))
//                .doOnSuccess(v -> log.info("Successfully deleted order: {}", orderId))
//                .doOnError(e -> log.error("Error deleting order {}: {}", orderId, e.getMessage()));
//    }
//
//    public Flux<Order> getOrdersByCustomerId(String customerId) {
//        log.info("Fetching orders for customer: {}", customerId);
//        return orderRepository.findByCustomerId(customerId)
//                .doOnComplete(() -> log.info("Successfully retrieved orders for customer: {}", customerId))
//                .doOnError(e -> log.error("Error retrieving orders for customer {}: {}", customerId, e.getMessage()));
//    }
//
//    public Mono<Void> deleteCustomerOrders(String customerId) {
//        log.info("Deleting all orders for customer: {}", customerId);
//        return orderRepository.deleteByCustomerId(customerId)
//                .doOnSuccess(v -> log.info("Successfully deleted orders for customer: {}", customerId))
//                .doOnError(e -> log.error("Error deleting orders for customer {}: {}", customerId, e.getMessage()));
//    }
//
//    private Order mapToOrder(OrderDTO dto) {
//        Order order = new Order();
//        order.setCustomerId(dto.getCustomerId());
//        order.setOrderDate(dto.getOrderDate() != null ? dto.getOrderDate() : LocalDate.now());
//        order.setTotalAmount(dto.getTotalAmount());
//        order.setShippingAddress(dto.getShippingAddress());
//        return order;
//    }
//
//    private void updateOrderFromDTO(Order order, OrderDTO dto) {
//        if (dto.getTotalAmount() != null) {
//            order.setTotalAmount(dto.getTotalAmount());
//        }
//        if (dto.getShippingAddress() != null) {
//            order.setShippingAddress(dto.getShippingAddress());
//        }
//        if (dto.getOrderDate() != null) {
//            order.setOrderDate(dto.getOrderDate());
//        }
//    }
//
//    private Mono<EnrichedOrderDTO> enrichOrder(Order order) {
//        return Mono.fromCallable(() -> {
//            try {
//                CustomerDTO customer = customerClient.getCustomer(order.getCustomerId());
//                List<OrderItemDTO> items = orderItemClient.getOrderItemsByOrderId(order.getId());
//                return createEnrichedOrderDTO(order, items, customer);
//            } catch (FeignException e) {
//                log.error("Error enriching order: {}", e.getMessage());
//                throw new RuntimeException("Failed to enrich order: " + e.getMessage());
//            }
//        }).subscribeOn(Schedulers.boundedElastic());
//    }
//
//    private EnrichedOrderDTO createEnrichedOrderDTO(Order order, List<OrderItemDTO> items, CustomerDTO customer) {
//        EnrichedOrderDTO dto = new EnrichedOrderDTO();
//        dto.setId(order.getId());
//        dto.setCustomerName(customer.getUsername());
//        dto.setOrderDate(order.getOrderDate().toString());
//        dto.setTotalAmount(order.getTotalAmount());
//        dto.setShippingAddress(order.getShippingAddress());
//        dto.setItems(mapToEnrichedItems(items));
//        return dto;
//    }
//
//    private List<EnrichedOrderDTO.EnrichedOrderItemDTO> mapToEnrichedItems(List<OrderItemDTO> items) {
//        return items.stream()
//                .map(item -> {
//                    EnrichedOrderDTO.EnrichedOrderItemDTO enrichedItem = new EnrichedOrderDTO.EnrichedOrderItemDTO();
//                    enrichedItem.setQuantity(item.getQuantity());
//                    enrichedItem.setPrice(item.getPrice());
//                    // Add any additional enriched item fields here
//                    return enrichedItem;
//                })
//                .collect(Collectors.toList());
//    }
//}

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemClient orderItemClient;

    @Autowired
    private CustomerClient customerClient;

    public Order createOrder(OrderDTO orderDTO) {
        try {
            // Create new order
            Order order = new Order();
            order.setId(orderDTO.getId());  // Allow custom ID if provided
            order.setCustomerId(orderDTO.getCustomerId());
            order.setOrderDate(orderDTO.getOrderDate() != null ? orderDTO.getOrderDate() : LocalDate.now());
            order.setTotalAmount(orderDTO.getTotalAmount());
            order.setShippingAddress(orderDTO.getShippingAddress());

            // Save initial order
            Order savedOrder = orderRepository.save(order);
            log.info("Order saved with ID: " + savedOrder.getId());

            // Only process items if they exist
            if (orderDTO.getItems() != null && !orderDTO.getItems().isEmpty()) {
                try {
                    List<OrderItemDTO> items = orderItemClient.createOrderItems(orderDTO.getItems());
                    savedOrder.setOrderItemIds(items.stream()
                            .map(OrderItemDTO::getId)
                            .collect(Collectors.toList()));
                    savedOrder = orderRepository.save(savedOrder);
                } catch (Exception e) {
                    log.error("Error creating order items: " + e.getMessage());
                    // Continue without items if service is down
                }
            }

            return savedOrder;

        } catch (Exception e) {
            log.error("Error in createOrder: " + e.getMessage());
            throw new RuntimeException("Could not create order: " + e.getMessage());
        }
    }

    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public OrderDTO getOrderWithDetails(String orderId) {
        // Get the order
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            return null;
        }

        Order order = orderOptional.get();

        // Create OrderDTO and set basic details
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCustomerId(order.getCustomerId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setShippingAddress(order.getShippingAddress());

        // Get and set order items
        List<OrderItemDTO> items = orderItemClient.getOrderItemsByOrderId(orderId);
        orderDTO.setItems(items);

        return orderDTO;
    }

    public Order updateOrder(String orderId, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(orderId).orElse(null);
        if (existingOrder == null) return null;

        existingOrder.setTotalAmount(orderDTO.getTotalAmount());
        existingOrder.setShippingAddress(orderDTO.getShippingAddress());
        existingOrder.setOrderDate(orderDTO.getOrderDate());

        List<OrderItemDTO> items = orderItemClient.updateOrderItems(orderId, orderDTO.getItems());
        existingOrder.setOrderItemIds(items.stream()
                .map(OrderItemDTO::getId)
                .collect(Collectors.toList()));

        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(String orderId) {
        orderItemClient.deleteOrderItems(orderId);
        orderRepository.deleteById(orderId);
    }

    public List<Order> getOrdersByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public void deleteCustomerOrders(String customerId) {
        orderRepository.deleteByCustomerId(customerId);
    }
}