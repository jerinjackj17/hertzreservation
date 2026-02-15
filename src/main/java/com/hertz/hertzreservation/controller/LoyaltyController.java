package com.hertz.hertzreservation.controller;

import com.hertz.hertzreservation.dto.EligibilityRequestDTO;
import com.hertz.hertzreservation.dto.LoyaltyRequestDTO;
import com.hertz.hertzreservation.dto.LoyaltyResponseDTO;
import com.hertz.hertzreservation.dto.ProductResponseDTO;
import com.hertz.hertzreservation.service.LoyaltyService;
import com.hertz.hertzreservation.service.ProductEligibilityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loyalty")
public class LoyaltyController {

    private final LoyaltyService loyaltyService;
    private final ProductEligibilityService productEligibilityService;

    public LoyaltyController(LoyaltyService loyaltyService,
                             ProductEligibilityService productEligibilityService) {
        this.loyaltyService = loyaltyService;
        this.productEligibilityService = productEligibilityService;
    }

    // API 1 — Determine Loyalty Level
    @PostMapping("/level")
    public LoyaltyResponseDTO determineLevel(
            @RequestBody LoyaltyRequestDTO request) {

        return loyaltyService.determineLoyaltyLevel(request);
    }

    // API 2 — Determine Eligible Products
    @PostMapping("/eligible-products")
    public List<ProductResponseDTO> getEligibleProducts(
            @RequestBody EligibilityRequestDTO request) {

        return productEligibilityService.getEligibleProducts(request);
    }
}
