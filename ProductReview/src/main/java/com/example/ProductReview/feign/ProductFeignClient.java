package com.example.ProductReview.feign;

import com.example.ProductReview.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "http://localhost:8081/products") // Use the URL or service discovery
public interface ProductFeignClient {

    @GetMapping("/{id}")
    ProductDTO getProductById(@PathVariable String id);
}
