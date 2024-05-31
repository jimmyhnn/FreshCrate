package com.MJJLB.FreshCrate.service.impl;

import com.MJJLB.FreshCrate.dto.CustomerDTO;
import com.MJJLB.FreshCrate.repository.CustomerRepository;
import com.MJJLB.FreshCrate.service.AddressService;
import com.MJJLB.FreshCrate.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private AddressService addressService;
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AddressService addressService){
        this.customerRepository = customerRepository;
        this.addressService = addressService;
    }
    @Override
    public void createCustomer(CustomerDTO customer) {
        customerRepository.createCustomer(customer);
    }

    @Override
    public List<CustomerDTO> getCustomer(Integer id,
                                         String firstName,
                                         String lastName,
                                         String email,
                                         String phoneNumber) {
        return customerRepository.getCustomer(id, firstName, lastName, email, phoneNumber);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @Override
    public void updateCustomerEmail(int id, String email) {
        customerRepository.updateCustomerEmail(id, email);
    }

    @Override
    public void updateCustomerPhoneNumber(int id, String phoneNumber) {
        customerRepository.updateCustomerPhoneNumber(id, phoneNumber);
    }

    @Override
    @Transactional
    public void deleteCustomerById(int id) {
        addressService.deleteAddress(id);
        customerRepository.deleteCustomerById(id);
    }
}
