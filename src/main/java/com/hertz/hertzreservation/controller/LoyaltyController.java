package com.hertz.hertzreservation.controller;

import com.hertz.hertzreservation.dto.EligibilityRequestDTO;
import com.hertz.hertzreservation.dto.LoyaltyRequestDTO;
import com.hertz.hertzreservation.dto.LoyaltyResponseDTO;
import com.hertz.hertzreservation.dto.ProductResponseDTO;
import com.hertz.hertzreservation.service.LoyaltyService;
import com.hertz.hertzreservation.service.ProductEligibilityService;

import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/loyalty")
public class LoyaltyController {

    private final LoyaltyService loyaltyService;
    private final ProductEligibilityService productEligibilityService;

    // Logger for controller requests
    private static final Logger logger =
            LoggerFactory.getLogger(LoyaltyController.class);

    public LoyaltyController(LoyaltyService loyaltyService,
                             ProductEligibilityService productEligibilityService) {
        this.loyaltyService = loyaltyService;
        this.productEligibilityService = productEligibilityService;
    }

    // API 1 — Determine Loyalty Level
    @PostMapping("/level")
    public LoyaltyResponseDTO determineLevel(
            @RequestBody LoyaltyRequestDTO request) {

        logger.info("Received request to determine loyalty level. totalPurchaseAmount={}",
                request.getTotalPurchaseAmountYearly());

        LoyaltyResponseDTO response = loyaltyService.determineLoyaltyLevel(request);

        logger.info("Loyalty level determined successfully: {}",
                response.getMembershipLevel());

        return response;
    }

    // API 2 — Determine Eligible Products
    @PostMapping("/eligible-products")
    public List<ProductResponseDTO> getEligibleProducts(
            @RequestBody EligibilityRequestDTO request) {

        logger.info("Received request to fetch eligible products for reservationDate={}",
                request.getReservationDate());

        List<ProductResponseDTO> products =
                productEligibilityService.getEligibleProducts(request);

        logger.info("Total eligible products returned={}", products.size());

        return products;
    }
}