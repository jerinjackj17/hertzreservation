package com.hertz.hertzreservation.service;

import com.hertz.hertzreservation.dto.LoyaltyRequestDTO;
import com.hertz.hertzreservation.dto.LoyaltyResponseDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LoyaltyService {

    private static final Logger logger =
            LoggerFactory.getLogger(LoyaltyService.class);

    public LoyaltyResponseDTO determineLoyaltyLevel(LoyaltyRequestDTO request) {

        logger.info("Calculating loyalty level for yearly purchase={}",
                request.getTotalPurchaseAmountYearly());

        BigDecimal total = request.getTotalPurchaseAmountYearly();

        String level;

        if (total.compareTo(BigDecimal.valueOf(10000)) >= 0) {
            level = "PLATINUM";
        } 
        else if (total.compareTo(BigDecimal.valueOf(5000)) >= 0) {
            level = "GOLD";
        } 
        else if (total.compareTo(BigDecimal.valueOf(1000)) >= 0) {
            level = "SILVER";
        } 
        else {
            level = "BRONZE";
        }

        logger.info("Loyalty level determined={}", level);

        return new LoyaltyResponseDTO(level);
    }
}