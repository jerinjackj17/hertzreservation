package com.hertz.hertzreservation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/*
 * DTO used by customer-service to call the email microservice API.
 * Structure must match the request expected by email-service.
 */
public class EmailRequestDTO {

    // Full name of the customer receiving the birthday email
    @NotBlank(message = "Customer name is required")
    private String customerName;

    // Email address where the birthday message will be sent
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    public EmailRequestDTO() {}

    public EmailRequestDTO(String customerName, String email) {
        this.customerName = customerName;
        this.email = email;
    }

    // Getter for customerName
    public String getCustomerName() {
        return customerName;
    }

    // Setter for customerName
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }
}