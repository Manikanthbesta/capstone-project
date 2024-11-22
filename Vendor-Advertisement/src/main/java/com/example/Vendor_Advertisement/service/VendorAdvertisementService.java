package com.example.Vendor_Advertisement.service;

import com.example.Vendor_Advertisement.dto.VendorAdvertisementDTO;
import com.example.Vendor_Advertisement.model.VendorAdvertisement;
import com.example.Vendor_Advertisement.repository.VendorAdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VendorAdvertisementService {

    private final VendorAdvertisementRepository repository;

    // Convert entity to DTO
    private VendorAdvertisementDTO convertToDTO(VendorAdvertisement advertisement) {
        VendorAdvertisementDTO dto = new VendorAdvertisementDTO();
        dto.setAdvertisementid(advertisement.getAdvertisementid());
        dto.setVendorId(advertisement.getVendorId());
//        dto.setAdvertisementImageUrl(advertisement.getAdvertisementImageUrl()); // Optional, if needed
        return dto;
    }

    // Convert DTO to entity
    private VendorAdvertisement convertToEntity(VendorAdvertisementDTO dto) {
        VendorAdvertisement advertisement = new VendorAdvertisement();
        advertisement.setAdvertisementid(dto.getAdvertisementid());
        advertisement.setVendorId(dto.getVendorId());
//        advertisement.setAdvertisementImageUrl(dto.getAdvertisementImageUrl()); // Optional, if needed
        return advertisement;
    }

    // Create a new advertisement
    public Mono<VendorAdvertisementDTO> createAdvertisement(VendorAdvertisementDTO advertisementDTO) {
        // Convert DTO to entity before saving
        VendorAdvertisement advertisement = convertToEntity(advertisementDTO);
        return repository.save(advertisement)
                .map(this::convertToDTO);  // Convert saved entity back to DTO
    }

    // Get all advertisements
    public Flux<VendorAdvertisementDTO> getAllAdvertisements() {
        return repository.findAll()
                .map(this::convertToDTO);  // Convert each entity to DTO
    }

    // Get advertisement by ID
    public Mono<VendorAdvertisementDTO> getAdvertisementById(String advertisementid) {
        return repository.findById(advertisementid)
                .map(this::convertToDTO)  // Convert entity to DTO
                .switchIfEmpty(Mono.empty());
    }

    // Get advertisements by Vendor ID
    public Flux<VendorAdvertisementDTO> getAdvertisementsByVendorId(String vendorId) {
        return repository.findByVendorId(vendorId)
                .map(this::convertToDTO);  // Convert each entity to DTO
    }

    // Delete advertisement
    public Mono<Void> deleteAdvertisement(String advertisementid) {
        return repository.deleteById(advertisementid);
    }
}
