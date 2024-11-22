package com.example.Customer.repository;

import com.example.Customer.model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
    Mono<Customer> findByUsername(String username);
    Mono<Customer> findByEmail(String email);
}