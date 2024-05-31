package com.MJJLB.FreshCrate.controller;

import com.MJJLB.FreshCrate.dto.CustomerAddressDTO;
import com.MJJLB.FreshCrate.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Shipping Address Management", description = "APIs related to managing shipping addresses of customers")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/createAddress")
    @Operation(summary = "Create a new shipping address")
    public ResponseEntity<String> createAddress(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the address to be created")
                                                    @RequestBody(required = true) CustomerAddressDTO address) {
        addressService.createAddress(address);
        return ResponseEntity.ok("Address Created Successfully");
    }

    @PutMapping("/updateAddress")
    @Operation(summary = "Update an existing shipping address of a customer. You can update any combination of street, city, zipcode, state id")
    public ResponseEntity<String> updateAddress(@Parameter(description = "ID of the customer whose address is to be updated") @RequestParam(required = true) Integer customerId,
                                                @Parameter(description = "Street name of the address") @RequestParam(required = false) String street,
                                                @Parameter(description = "City of the address") @RequestParam(required = false) String city,
                                                @Parameter(description = "ZIP code of the address") @RequestParam(required = false) String zipCode,
                                                @Parameter(description = "State ID of the address") @RequestParam(required = false) String stateId) {
        addressService.updateAddress(customerId, street, city, zipCode, stateId);
        return ResponseEntity.ok("Address Updated Successfully");
    }

    @GetMapping("/getAddressByCustomerId")
    @Operation(summary = "Get full shipping addresses of a customer by their customer ID")
    public ResponseEntity<List<CustomerAddressDTO>> getAddressByCustomerId(@Parameter(description = "ID of the customer whose addresses are to be retrieved") @RequestParam (required = true) Integer customerId) {
        List<CustomerAddressDTO> addresses = addressService.getAddressByCustomerId(customerId);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/getAllAddresses")
    @Operation(summary = "Get All addresses in our db")
    public ResponseEntity<List<CustomerAddressDTO>> getAllAddresses() {
        List<CustomerAddressDTO> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    @DeleteMapping("/deleteAddress")
    @Operation(summary = "Delete all addresses associated with a customer")
    public ResponseEntity<String> deleteAddress(@Parameter(description = "ID of the customer whose address is to be deleted") @RequestParam(required = true) Integer customerId) {
        addressService.deleteAddress(customerId);
        return ResponseEntity.ok("Address deleted successfully");
    }
}
