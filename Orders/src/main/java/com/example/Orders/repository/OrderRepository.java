package com.example.Orders.repository;

import com.example.Orders.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByCustomerId(String customerId);
    void deleteByCustomerId(String customerId);
    List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
    List<Order> findByTotalAmountGreaterThan(Double amount);
    List<Order> findByTotalAmountLessThan(Double amount);
}