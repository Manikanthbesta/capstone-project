package com.example.OrderItem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderItemApplication.class, args);
	}

}
