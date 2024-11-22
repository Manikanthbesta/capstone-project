package com.example.Product.repository;

import com.example.Product.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Flux<Product> findByVendorId(String vendorId);
    Flux<Product> findByCategory(String category);
}