package com.hertz.hertzreservation.repository;

import com.hertz.hertzreservation.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/*
 * This repository talks directly to MongoDB.
 * Spring automatically generates implementation.
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

    // Custom query method using naming convention
    Optional<Customer> findByEmail(String email);

    void deleteByEmail(String email);
}