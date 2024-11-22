package com.example.Customer.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private String id;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private String firstName;
    private String lastName;
    private String profileImageUrl;
}