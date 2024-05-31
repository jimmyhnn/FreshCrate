package com.MJJLB.FreshCrate.service.impl;

import com.MJJLB.FreshCrate.dto.CustomerAddressDTO;
import com.MJJLB.FreshCrate.repository.AddressRepository;
import com.MJJLB.FreshCrate.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    @Override
    public void createAddress(CustomerAddressDTO address) {
        addressRepository.createAddress(address);
    }

    @Override
    public void updateAddress(Integer customerId, String street, String city, String zipCode, String stateId) {
        addressRepository.updateAddress(customerId, street, city, zipCode, stateId);
    }

    @Override
    public void deleteAddress(Integer customerId) {
        addressRepository.deleteAddress(customerId);
    }

    @Override
    public List<CustomerAddressDTO> getAddressByCustomerId(Integer customerId) {
        return addressRepository.getAddressByCustomerId(customerId);
    }

    @Override
    public List<CustomerAddressDTO> getAllAddresses() {
        return addressRepository.getAllAddresses();
    }
}
