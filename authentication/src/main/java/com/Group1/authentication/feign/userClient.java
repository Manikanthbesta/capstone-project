package com.Group1.authentication.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service", url = "http://localhost:9996")
public interface userClient {

    @PostMapping("/create/{id}")
    public String create(@PathVariable String id);
}
