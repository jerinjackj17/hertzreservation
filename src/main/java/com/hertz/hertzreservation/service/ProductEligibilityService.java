package com.hertz.hertzreservation.service;

import com.hertz.hertzreservation.dto.EligibilityRequestDTO;
import com.hertz.hertzreservation.dto.ProductResponseDTO;
import com.hertz.hertzreservation.entity.ProductEntity;
import com.hertz.hertzreservation.repository.ProductRepository;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductEligibilityService {

    // Logger for product eligibility evaluation
    private static final Logger logger =
            LoggerFactory.getLogger(ProductEligibilityService.class);

    private final ProductRepository productRepository;
    private final EligibilityService eligibilityService;

    public ProductEligibilityService(ProductRepository productRepository,
                                     EligibilityService eligibilityService) {
        this.productRepository = productRepository;
        this.eligibilityService = eligibilityService;
    }

    public List<ProductResponseDTO> getEligibleProducts(EligibilityRequestDTO request) {

        logger.info("Starting product eligibility check for reservationDate={}",
                request.getReservationDate());

        // Step 1: Check customer eligibility
        if (!eligibilityService.isEligible(request)) {
            logger.warn("Customer is not eligible for any products");
            return List.of();
        }

        logger.info("Customer passed eligibility checks");

        // Step 2: Fetch all products from DB
        logger.debug("Fetching products from database");

        List<ProductEntity> products = productRepository.findAll();

        logger.info("Total products retrieved from database={}", products.size());

        // Step 3: Apply product-specific rules
        List<ProductResponseDTO> eligibleProducts = products.stream()
                .filter(product -> isDateValid(product, request))
                .filter(product -> isLargeVehicleAllowed(product, request))
                .filter(product -> isSportsVehicleAllowed(product, request))
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        logger.info("Total eligible products returned={}", eligibleProducts.size());

        return eligibleProducts;
    }

    private boolean isDateValid(ProductEntity product, EligibilityRequestDTO request) {

        boolean valid =
                !request.getReservationDate().isBefore(product.getValidFrom()) &&
                !request.getReservationDate().isAfter(product.getValidTo());

        if (!valid) {
            logger.debug("Product filtered due to invalid reservation date. productId={}",
                    product.getProductId());
        }

        return valid;
    }

    private boolean isLargeVehicleAllowed(ProductEntity product, EligibilityRequestDTO request) {

        if ("LARGE".equalsIgnoreCase(product.getSize())) {

            boolean allowed =
                    request.getAnnualIncome()
                            .compareTo(java.math.BigDecimal.valueOf(20000)) >= 0;

            if (!allowed) {
                logger.debug("Product filtered due to insufficient income for LARGE vehicle. productId={}",
                        product.getProductId());
            }

            return allowed;
        }

        return true;
    }

    private boolean isSportsVehicleAllowed(ProductEntity product, EligibilityRequestDTO request) {

        if ("SPORTS".equalsIgnoreCase(product.getType())) {

            boolean allowed = request.getAge() >= 25;

            if (!allowed) {
                logger.debug("Product filtered due to age restriction for SPORTS vehicle. productId={}",
                        product.getProductId());
            }

            return allowed;
        }

        return true;
    }

    private ProductResponseDTO convertToDTO(ProductEntity product) {

        logger.debug("Mapping ProductEntity to ProductResponseDTO productId={}",
                product.getProductId());

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