package com.MJJLB.FreshCrate.service;

import com.MJJLB.FreshCrate.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    // Create a new customer (used for registration)
    void createCustomer(CustomerDTO customer);

    // Get customer by optional filters
    List<CustomerDTO> getCustomer(Integer id,
                                  String firstName,
                                  String lastName,
                                  String email,
                                  String phoneNumber);

    // Get all customers
    List<CustomerDTO> getAllCustomers();

    // Update customer email
    void updateCustomerEmail(int id, String email);

    // Update customer phone number
    void updateCustomerPhoneNumber(int id, String phoneNumber);

    // Delete a customer by ID
    void deleteCustomerById(int id);
}


