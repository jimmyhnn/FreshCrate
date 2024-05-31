package com.MJJLB.FreshCrate.repository;

import com.MJJLB.FreshCrate.dto.CustomerDTO;

import java.util.List;

public interface CustomerRepository {

    void createCustomer(CustomerDTO customer);

    List<CustomerDTO> getCustomer(Integer id, String firstName, String lastName, String email, String phoneNumber);

    List<CustomerDTO> getAllCustomers();

    void updateCustomerEmail(int id, String email);

    void updateCustomerPhoneNumber(int id, String phoneNumber);

    void deleteCustomerById(int id);
}
