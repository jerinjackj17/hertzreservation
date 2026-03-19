package com.hertz.hertzreservation.service;

import com.hertz.hertzreservation.dto.EligibilityRequestDTO;
import com.hertz.hertzreservation.dto.ProductResponseDTO;
import com.hertz.hertzreservation.dto.VehicleEventDTO;
import com.hertz.hertzreservation.entity.ProductEntity;
import com.hertz.hertzreservation.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductEligibilityService {

    // logger for product eligibility evaluation
    private static final Logger logger =
            LoggerFactory.getLogger(ProductEligibilityService.class);

    private final ProductRepository productRepository;
    private final EligibilityService eligibilityService;

    // used to call kafka event service when no products found
    private final RestTemplate restTemplate = new RestTemplate();

    // kafka event service URL
    private static final String EVENT_SERVICE_URL =
            "http://kafka-event-service:8082/events/vehicle";

    // readable timestamp format e.g. "2026-03-18 14:32:05"
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ProductEligibilityService(ProductRepository productRepository,
                                     EligibilityService eligibilityService) {
        this.productRepository = productRepository;
        this.eligibilityService = eligibilityService;
    }

    public List<ProductResponseDTO> getEligibleProducts(EligibilityRequestDTO request) {

        logger.info("Starting product eligibility check for reservationDate={}",
                request.getReservationDate());

        // step 1: check customer eligibility — if they fail, just return empty, no event
        if (!eligibilityService.isEligible(request)) {
            logger.warn("Customer failed eligibility check — no event sent");
            return List.of();
        }

        logger.info("Customer passed eligibility checks");

        // step 2: fetch all products from DB
        logger.debug("Fetching products from database");

        List<ProductEntity> products = productRepository.findAll();

        logger.info("Total products retrieved from database={}", products.size());

        // step 3: apply product-specific rules
        List<ProductResponseDTO> eligibleProducts = products.stream()
                .filter(product -> isDateValid(product, request))
                .filter(product -> isLargeVehicleAllowed(product, request))
                .filter(product -> isSportsVehicleAllowed(product, request))
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        logger.info("Total eligible products returned={}", eligibleProducts.size());

        // step 4: customer passed eligibility but no products matched — fire event
        if (eligibleProducts.isEmpty()) {
            logger.warn("Customer eligible but no products found — publishing VEHICLE_NOT_AVAILABLE");
            publishNotAvailableEvent(request);
        }

        return eligibleProducts;
    }

    // only called when customer passes eligibility but no products are found
    private void publishNotAvailableEvent(EligibilityRequestDTO request) {

        try {

            // build readable summary of what the customer searched for
            String searchCriteria = "Name: " + request.getName()
                    + ", Age: " + request.getAge()
                    + ", Income: " + request.getAnnualIncome()
                    + ", Reservation Date: " + request.getReservationDate();

            // readable local timestamp for kafka console visibility
            String eventTime = LocalDateTime.now().format(FORMATTER);

            VehicleEventDTO event = new VehicleEventDTO();
            event.setEventType("VEHICLE_NOT_AVAILABLE");
            event.setCustomerName(request.getName());
            event.setCustomerEmail(request.getEmail());
            event.setSearchCriteria(searchCriteria);
            event.setEventTime(eventTime);

            logger.info("Publishing VEHICLE_NOT_AVAILABLE event for email={}",
                    request.getEmail());

            restTemplate.postForObject(EVENT_SERVICE_URL, event, String.class);

            logger.info("VEHICLE_NOT_AVAILABLE event published successfully");

        } catch (Exception ex) {
            // log but don't crash — event failure should not block the API response
            logger.error("Failed to publish VEHICLE_NOT_AVAILABLE event", ex);
        }
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