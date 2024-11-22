package com.example.Vendor_Advertisement.controller;

import com.example.Vendor_Advertisement.dto.VendorAdvertisementDTO;  // Import the DTO
import com.example.Vendor_Advertisement.model.VendorAdvertisement;
import com.example.Vendor_Advertisement.service.VendorAdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/advertisements")
@RequiredArgsConstructor
@CrossOrigin
public class VendorAdvertisementController {

    private final VendorAdvertisementService service;

    @PostMapping
    public Mono<VendorAdvertisementDTO> createAdvertisement(@RequestBody VendorAdvertisementDTO advertisementDTO) {
        return service.createAdvertisement(advertisementDTO);
    }

    @GetMapping
    public Flux<VendorAdvertisementDTO> getAllAdvertisements() {
        return service.getAllAdvertisements();
    }

    @GetMapping("/{advertisementid}")
    public Mono<VendorAdvertisementDTO> getAdvertisementById(@PathVariable String advertisementid) {
        return service.getAdvertisementById(advertisementid);
    }

    @GetMapping("/vendor/{vendorId}")
    public Flux<VendorAdvertisementDTO> getAdvertisementsByVendorId(@PathVariable String vendorId) {
        return service.getAdvertisementsByVendorId(vendorId);
    }

    @DeleteMapping("/{advertisementid}")
    public Mono<Void> deleteAdvertisement(@PathVariable String advertisementid) {
        return service.deleteAdvertisement(advertisementid);
    }
}
