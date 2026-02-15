package com.hertz.hertzreservation.service;

import com.hertz.hertzreservation.dto.EligibilityRequestDTO;
import com.hertz.hertzreservation.dto.ProductResponseDTO;
import com.hertz.hertzreservation.entity.ProductEntity;
import com.hertz.hertzreservation.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductEligibilityService {

    private final ProductRepository productRepository;
    private final EligibilityService eligibilityService;

    public ProductEligibilityService(ProductRepository productRepository,
                                     EligibilityService eligibilityService) {
        this.productRepository = productRepository;
        this.eligibilityService = eligibilityService;
    }

    public List<ProductResponseDTO> getEligibleProducts(EligibilityRequestDTO request) {

        // Step 1: Check customer eligibility
        if (!eligibilityService.isEligible(request)) {
            return List.of();
        }

        // Step 2: Fetch all products from DB
        List<ProductEntity> products = productRepository.findAll();

        // Step 3: Apply product-specific rules
        return products.stream()
                .filter(product -> isDateValid(product, request))
                .filter(product -> isLargeVehicleAllowed(product, request))
                .filter(product -> isSportsVehicleAllowed(product, request))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private boolean isDateValid(ProductEntity product, EligibilityRequestDTO request) {
        return !request.getReservationDate().isBefore(product.getValidFrom())
                && !request.getReservationDate().isAfter(product.getValidTo());
    }

    private boolean isLargeVehicleAllowed(ProductEntity product, EligibilityRequestDTO request) {
        if ("LARGE".equalsIgnoreCase(product.getSize())) {
            return request.getAnnualIncome().compareTo(java.math.BigDecimal.valueOf(20000)) >= 0;
        }
        return true;
    }

    private boolean isSportsVehicleAllowed(ProductEntity product, EligibilityRequestDTO request) {
        if ("SPORTS".equalsIgnoreCase(product.getType())) {
            return request.getAge() >= 25;
        }
        return true;
    }

    private ProductResponseDTO convertToDTO(ProductEntity product) {
        return new ProductResponseDTO(
                product.getProductId(),
                product.getCategory(),
                product.getSize(),
                product.getType(),
                product.getDriveType(),
                product.getDuration(),
                product.getPrice(),
                product.getValidFrom(),
                product.getValidTo()
        );
    }
}
