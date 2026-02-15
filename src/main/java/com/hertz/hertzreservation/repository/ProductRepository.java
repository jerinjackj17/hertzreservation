package com.hertz.hertzreservation.repository;

import com.hertz.hertzreservation.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
