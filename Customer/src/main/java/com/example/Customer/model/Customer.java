package com.example.Customer.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "customers")
public class Customer {

    @Id
    private String userId;
    private String username;
    private String passwordHash;
    private String email;
    private String phoneNumber;
    private String address;
    private String firstName;
    private String lastName;
    private String profileImageUrl;
}