package com.example.Orders.feign;

import com.example.Orders.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "${customer-service.url:http://localhost:8081}")
public interface CustomerClient {
    @GetMapping("/customers/{id}")
    CustomerDTO getCustomer(@PathVariable String id);
}