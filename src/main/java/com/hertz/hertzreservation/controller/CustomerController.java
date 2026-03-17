package com.hertz.hertzreservation.controller;

import com.hertz.hertzreservation.dto.CustomerRequestDTO;
import com.hertz.hertzreservation.dto.CustomerResponseDTO;
import com.hertz.hertzreservation.service.CustomerService;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerService service;

    // Logger instance for this controller
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    // Create Customer
    @PostMapping
    public CustomerResponseDTO createCustomer(@Valid @RequestBody CustomerRequestDTO request) {

        // Log incoming request
        logger.info("Received request to create customer with email={}", request.getEmail());

        CustomerResponseDTO response = service.createCustomer(request);

        // Log successful response
        logger.info("Customer created successfully with id={}", response.getId());

        return response;
    }

    // Update Customer
    @PutMapping("/{id}")
    public CustomerResponseDTO updateCustomer(@PathVariable String id,
                                              @RequestBody CustomerRequestDTO request) {

        // Log update request
        logger.info("Received request to update customer with id={}", id);

        CustomerResponseDTO response = service.updateCustomer(id, request);

        // Log update completion
        logger.info("Customer updated successfully with id={}", id);

        return response;
    }

    // Delete customer by id
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable String id) {

        // Log delete request
        logger.info("Received request to delete customer with id={}", id);

        service.deleteCustomer(id);

        // Log delete completion
        logger.info("Customer deleted successfully with id={}", id);
    }

    // Get customer by email
    @GetMapping("/email/{email}")
    public CustomerResponseDTO getByEmail(@PathVariable String email) {

        // Log fetch request
        logger.info("Received request to fetch customer with email={}", email);

        CustomerResponseDTO response = service.getCustomerByEmail(email);

        // Log successful fetch
        logger.info("Customer fetched successfully with email={}", email);

        return response;
    }

    // Get all customers
    @GetMapping
    public List<CustomerResponseDTO> getAllCustomers() {

        // Log list retrieval
        logger.info("Received request to fetch all customers");

        List<CustomerResponseDTO> customers = service.getAllCustomers();

        // Log number of records returned
        logger.info("Returned {} customers", customers.size());

        return customers;
    }
}