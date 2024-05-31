package com.MJJLB.FreshCrate.repository;

import com.MJJLB.FreshCrate.dto.CustomerAddressDTO;

import java.util.List;

public interface AddressRepository {

    void createAddress(CustomerAddressDTO address);

    void updateAddress(Integer customerId, String street, String city, String zipCode, String stateId);

    void deleteAddress(Integer customerId);

    List<CustomerAddressDTO> getAddressByCustomerId(Integer customerId);

    List<CustomerAddressDTO> getAllAddresses();
}
