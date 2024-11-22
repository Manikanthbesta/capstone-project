package com.example.Vendor.feign;

import com.example.Vendor.dto.VendorAdvertisementDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

@FeignClient(name = "vendor-advertisement-service")
public interface VendorAdvertisementClient {

    @GetMapping("/advertisements/vendor/{vendorId}")
    Flux<VendorAdvertisementDTO> getAdvertisementsByVendorId(@PathVariable String vendorId);
}
