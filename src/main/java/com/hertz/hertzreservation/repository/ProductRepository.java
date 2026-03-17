package com.hertz.hertzreservation.repository;

import com.hertz.hertzreservation.entity.ProductEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

// public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
// }

public interface ProductRepository extends MongoRepository<ProductEntity, String> {
}