package com.hertz.hertzreservation.dto;

import java.time.LocalDate;

/*
 * Response body returned to the client for customer APIs.
 */
public class CustomerResponseDTO {

    private String id;
    private String firstName;
    private String lastName;

    // Date of birth returned to UI
    private LocalDate dateOfBirth;

    private String email;
    private Integer rentalsLastYear;

    public CustomerResponseDTO() {
    }
    // constructor used to create a CustomerResponseDTO object when returning data from the service layer
    public CustomerResponseDTO(String id,
                               String firstName,
                               String lastName,
                               LocalDate dateOfBirth,
                               String email,
                               Integer rentalsLastYear) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.rentalsLastYear = rentalsLastYear;
    }

    // ---------- Getters and Setters ----------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRentalsLastYear() {
        return rentalsLastYear;
    }

    public void setRentalsLastYear(Integer rentalsLastYear) {
        this.rentalsLastYear = rentalsLastYear;
    }
}