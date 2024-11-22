package com.example.Vendor_Advertisement.repository;


import com.example.Vendor_Advertisement.model.VendorAdvertisement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface VendorAdvertisementRepository extends ReactiveMongoRepository<VendorAdvertisement, String> {
    Flux<VendorAdvertisement> findByVendorId(String vendorId);
}