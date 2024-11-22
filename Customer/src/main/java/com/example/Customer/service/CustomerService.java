package com.example.Customer.service;

import com.example.Customer.dto.CustomerDTO;
import com.example.Customer.model.Customer;
import com.example.Customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;



    public Mono<CustomerDTO> createCustomer(Customer customer) {
        return customerRepository.save(customer)  // Save the customer object, MongoDB auto-generates the ID
                .map(this::convertToDTO);  // Convert to DTO after saving
    }



    public Mono<CustomerDTO> getCustomerById(String userId) {
        return customerRepository.findById(userId)
                .map(this::convertToDTO);
    }

    public Mono<CustomerDTO> getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username)
                .map(this::convertToDTO);
    }

    public Mono<CustomerDTO> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(this::convertToDTO);
    }

    public Mono<CustomerDTO> updateCustomer(String id, Customer customer) {
        return customerRepository.findById(id)
                .flatMap(existingCustomer -> {
                    existingCustomer.setUsername(customer.getUsername());
                    existingCustomer.setPasswordHash(customer.getPasswordHash());
                    existingCustomer.setEmail(customer.getEmail());
                    existingCustomer.setPhoneNumber(customer.getPhoneNumber());
                    existingCustomer.setAddress(customer.getAddress());
                    existingCustomer.setFirstName(customer.getFirstName());
                    existingCustomer.setLastName(customer.getLastName());
                    existingCustomer.setProfileImageUrl(customer.getProfileImageUrl());
                    return customerRepository.save(existingCustomer);
                })
                .map(this::convertToDTO);
    }

    public Mono<Void> deleteCustomer(String id) {
        return customerRepository.deleteById(id);
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getUserId().toString());
        dto.setUsername(customer.getUsername());
        dto.setEmail(customer.getEmail());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setAddress(customer.getAddress());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setProfileImageUrl(customer.getProfileImageUrl());
        return dto;
    }

    public Flux<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}