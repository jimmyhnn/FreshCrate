package com.MJJLB.FreshCrate.controller;

import com.MJJLB.FreshCrate.dto.CustomerDTO;
import com.MJJLB.FreshCrate.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Customer Management", description = "APIs related to managing customers")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping("/createCustomer")
    @Operation(summary = "Create a new customer")
    public ResponseEntity<String> createCustomer(
            @Parameter(description = "Details of the customer to be created") @RequestBody(required = true) CustomerDTO customer) {
        customerService.createCustomer(customer);
        return ResponseEntity.ok("Customer Created Successfully");
    }

    @GetMapping("/getCustomer")
    @Operation(summary = "Get customers by different filters. Filters can be any combination of: customer id, first name, last name, email, phone number")
    public ResponseEntity<List<CustomerDTO>> getCustomer(
            @Parameter(description = "ID of the customer") @RequestParam(required = false) Integer customerId,
            @Parameter(description = "First name of the customer") @RequestParam(required = false) String firstName,
            @Parameter(description = "Last name of the customer") @RequestParam(required = false) String lastName,
            @Parameter(description = "Email of the customer") @RequestParam(required = false) String email,
            @Parameter(description = "Phone number of the customer") @RequestParam(required = false) String phoneNumber) {
        List<CustomerDTO> customers = customerService.getCustomer(customerId, firstName, lastName, email, phoneNumber);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/getAllCustomers")
    @Operation(summary = "Get all customers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PutMapping("updateCustomerEmail")
    @Operation(summary = "Update customer email")
    public ResponseEntity<String> updateCustomerEmail(
            @Parameter(description = "ID of the customer whose email is to be updated") @RequestParam(required = true) int id,
            @Parameter(description = "New email address of the customer") @RequestParam(required = true) String email) {
        customerService.updateCustomerEmail(id, email);
        return ResponseEntity.ok("Customer email updated successfully.");
    }

    @PutMapping("updateCustomerPhoneNumber")
    @Operation(summary = "Update customer phone number")
    public ResponseEntity<String> updateCustomerPhoneNumber(
            @Parameter(description = "ID of the customer whose phone number is to be updated") @RequestParam(required = true) int id,
            @Parameter(description = "New phone number of the customer") @RequestParam(required = true) String phoneNumber) {
        customerService.updateCustomerPhoneNumber(id, phoneNumber);
        return ResponseEntity.ok("Customer phone number updated successfully.");
    }

    @DeleteMapping("/deleteCustomer")
    @Operation(summary = "Delete a customer based on their customer id")
    public ResponseEntity<String> deleteCustomer(
            @Parameter(description = "ID of the customer to be deleted") @RequestParam(required = true) int id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }
}
