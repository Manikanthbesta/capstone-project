package com.example.ProductReview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProductReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductReviewApplication.class, args);
	}

}
