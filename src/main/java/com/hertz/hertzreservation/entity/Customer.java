package com.hertz.hertzreservation.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "customers")
public class Customer {

    @Id
    private String id;

    private String firstName;
    private String lastName;

    // Date of birth used to detect birthday events
    private LocalDate dateOfBirth;

    @Indexed(unique = true)
    private String email;

    // For now, we store only the count of rentals in last year
    private Integer rentalsLastYear;

    public Customer() {}

    public Customer(String firstName,
                    String lastName,
                    LocalDate dateOfBirth,
                    String email,
                    Integer rentalsLastYear) {

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