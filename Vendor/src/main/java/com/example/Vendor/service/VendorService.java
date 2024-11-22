package com.example.Vendor.service;

import com.example.Vendor.dto.ReviewDTO;
import com.example.Vendor.dto.VendorDTO;
import com.example.Vendor.feign.ReviewClient;
import com.example.Vendor.feign.VendorAdvertisementClient;
import com.example.Vendor.model.Vendor;
import com.example.Vendor.repository.VendorRepository;
import com.example.Vendor.dto.VendorAdvertisementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private VendorAdvertisementClient vendorAdvertisementClient;  // Inject Feign client

    @Autowired
    private ReviewClient reviewClient; // Feign client to interact with the Review microservice

    // POST method to create a new vendor
    public Mono<Vendor> createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    // GET method to retrieve a vendor by ID
    public Mono<VendorDTO> getVendorById(String id) {
        return vendorRepository.findById(id)
                .map(this::convertToDTO);
    }

    // GET method to retrieve all vendors
    public Flux<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .map(this::convertToDTO);
    }

    // PUT method to update vendor details
    public Mono<VendorDTO> updateVendor(String id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .flatMap(existingVendor -> {
                    // Update only provided fields
                    existingVendor.setName(vendorDTO.getName() != null ? vendorDTO.getName() : existingVendor.getName());
                    existingVendor.setContactEmail(vendorDTO.getContactEmail() != null ? vendorDTO.getContactEmail() : existingVendor.getContactEmail());
                    existingVendor.setContactPhoneNumber(vendorDTO.getContactPhoneNumber() != null ? vendorDTO.getContactPhoneNumber() : existingVendor.getContactPhoneNumber());
                    existingVendor.setAddress(vendorDTO.getAddress() != null ? vendorDTO.getAddress() : existingVendor.getAddress());
                    existingVendor.setLocation(vendorDTO.getLocation() != null ? vendorDTO.getLocation() : existingVendor.getLocation());
                    existingVendor.setBusinessCategory(vendorDTO.getBusinessCategory() != null ? vendorDTO.getBusinessCategory() : existingVendor.getBusinessCategory());
                    existingVendor.setStoreName(vendorDTO.getStoreName() != null ? vendorDTO.getStoreName() : existingVendor.getStoreName());
                    existingVendor.setProfileImageUrl(vendorDTO.getProfileImageUrl() != null ? vendorDTO.getProfileImageUrl() : existingVendor.getProfileImageUrl());
                    // Save and return the updated vendor
                    return vendorRepository.save(existingVendor);
                })
                .map(this::convertToDTO);
    }

    // DELETE method to remove a vendor by ID
    public Mono<Void> deleteVendor(String id) {
        return vendorRepository.deleteById(id);
    }

    // Method to add a review for a vendor by calling Review microservice via Feign
    public Mono<ReviewDTO> addReviewForVendor(String vendorId, ReviewDTO reviewDTO) {
        return reviewClient.addReview(vendorId, reviewDTO) // Use Feign to add the review
                .flatMap(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        return Mono.just(response.getBody()); // Return the ReviewDTO if successful
                    } else {
                        return Mono.error(new RuntimeException("Failed to add review")); // Handle failure
                    }
                });
    }

    // Helper method to convert Vendor entity to DTO
    private VendorDTO convertToDTO(Vendor vendor) {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(vendor.getId());
        vendorDTO.setName(vendor.getName());
        vendorDTO.setContactEmail(vendor.getContactEmail());
        vendorDTO.setContactPhoneNumber(vendor.getContactPhoneNumber());
        vendorDTO.setAddress(vendor.getAddress());
        vendorDTO.setLocation(vendor.getLocation());
        vendorDTO.setBusinessCategory(vendor.getBusinessCategory());
        vendorDTO.setStoreName(vendor.getStoreName());
        vendorDTO.setProfileImageUrl(vendor.getProfileImageUrl());
        return vendorDTO;
    }

    // Method to retrieve all vendors directly (not through DTO)
    public Flux<Vendor> getAllVendorsDirectly() {
        return vendorRepository.findAll();
    }

    // Method to retrieve advertisements by Vendor ID (from VendorAdvertisement microservice)
    public Flux<VendorAdvertisementDTO> getAdvertisementsByVendorId(String vendorId) {
        return vendorAdvertisementClient.getAdvertisementsByVendorId(vendorId);  // Feign client to get VendorAdvertisements
    }
}
