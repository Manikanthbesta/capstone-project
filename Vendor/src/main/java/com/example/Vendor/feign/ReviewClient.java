package com.example.Vendor.feign;

import com.example.Vendor.dto.ReviewDTO;
import com.example.Vendor.dto.VendorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@FeignClient(name="review-service",url = "http://localhost:8089/reviews")
public interface ReviewClient {
    @PostMapping("/reviews/vendor/{id}")
    Mono<ResponseEntity<ReviewDTO>> addReview(
            @PathVariable("id") String vendorId,
            @RequestBody ReviewDTO reviewDTO);
}

