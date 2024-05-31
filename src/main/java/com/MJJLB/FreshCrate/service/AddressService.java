package com.MJJLB.FreshCrate.service;

import com.MJJLB.FreshCrate.dto.CustomerAddressDTO;

import java.util.List;

public interface AddressService {

    void createAddress(CustomerAddressDTO address);

    void updateAddress(Integer customerId, String street, String city, String zipCode, String stateId);

    void deleteAddress(Integer customerId);

    List<CustomerAddressDTO> getAddressByCustomerId(Integer customerId);

    List<CustomerAddressDTO> getAllAddresses();

}
