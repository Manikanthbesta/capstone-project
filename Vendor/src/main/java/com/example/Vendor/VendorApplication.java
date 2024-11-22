package com.example.Vendor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VendorApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendorApplication.class, args);
	}

}
