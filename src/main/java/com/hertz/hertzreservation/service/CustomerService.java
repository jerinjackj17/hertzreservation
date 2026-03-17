package com.hertz.hertzreservation.service;

import com.hertz.hertzreservation.dto.CustomerRequestDTO;
import com.hertz.hertzreservation.dto.CustomerResponseDTO;

import java.util.List;


public interface CustomerService {

    CustomerResponseDTO createCustomer(CustomerRequestDTO request);

    CustomerResponseDTO updateCustomer(String id, CustomerRequestDTO request);

    void deleteCustomer(String id);

    CustomerResponseDTO getCustomerByEmail(String email);

    List<CustomerResponseDTO> getAllCustomers();
}