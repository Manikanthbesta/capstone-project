package com.example.Vendor.controller;

import com.example.Vendor.model.Vendor;
import com.example.Vendor.dto.VendorDTO;
import com.example.Vendor.dto.ReviewDTO;
import com.example.Vendor.dto.VendorAdvertisementDTO;  // Make sure you import the correct DTO
import com.example.Vendor.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/vendors")
@CrossOrigin
public class VendorController {

    @Autowired
    private VendorService vendorService;

    // Create a new vendor
    @PostMapping
    public Mono<ResponseEntity<Vendor>> createVendor(@RequestBody Vendor vendor) {
        return vendorService.createVendor(vendor)
                .map(createdVendor -> ResponseEntity.status(HttpStatus.CREATED).body(createdVendor));
    }

    // Get all vendors directly (not through DTO) - Using ResponseEntity for consistency
    @GetMapping("/all")
    public Mono<ResponseEntity<Flux<Vendor>>> getAllVendorsDirectly() {
        return Mono.just(ResponseEntity.ok(vendorService.getAllVendorsDirectly()));
    }

    // Get a vendor by its ID (DTO format)
    @GetMapping("/{id}")
    public Mono<ResponseEntity<VendorDTO>> getVendorById(@PathVariable String id) {
        return vendorService.getVendorById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Get all vendors (DTO format)
    @GetMapping
    public Flux<VendorDTO> getAllVendors() {
        return vendorService.getAllVendors();
    }

    // Update vendor details
    @PutMapping("/{id}")
    public Mono<ResponseEntity<VendorDTO>> updateVendor(@PathVariable String id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.updateVendor(id, vendorDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Delete a vendor by its ID
    @DeleteMapping("/{id}")
    public Mono<Void> deleteVendor(@PathVariable String id) {
        return vendorService.deleteVendor(id);
    }

    // Add a review for a specific vendor
    @PostMapping("/{id}/reviews")
    public Mono<ResponseEntity<ReviewDTO>> addReview(@PathVariable String id, @RequestBody ReviewDTO reviewDTO) {
        return vendorService.addReviewForVendor(id, reviewDTO)
                .map(createdReview -> ResponseEntity.status(HttpStatus.CREATED).body(createdReview))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    // Get advertisements for a specific vendor - Return DTO instead of model
    @GetMapping("/{id}/advertisements")
    public Flux<VendorAdvertisementDTO> getAdvertisementsByVendorId(@PathVariable String id) {
        return vendorService.getAdvertisementsByVendorId(id);
    }
}
