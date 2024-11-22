package com.example.Vendor.repository;

import com.example.Vendor.model.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}