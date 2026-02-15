package com.hertz.hertzreservation.service;

import com.hertz.hertzreservation.dto.LoyaltyRequestDTO;
import com.hertz.hertzreservation.dto.LoyaltyResponseDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LoyaltyService {

    public LoyaltyResponseDTO determineLoyaltyLevel(LoyaltyRequestDTO request) {

        BigDecimal totalSpent = request.getTotalPurchaseAmountYearly();

        String level;

        if (totalSpent.compareTo(BigDecimal.valueOf(1500)) > 0) {
            level = "DIAMOND";
        } else if (totalSpent.compareTo(BigDecimal.valueOf(1000)) > 0) {
            level = "GOLD";
        } else if (totalSpent.compareTo(BigDecimal.valueOf(500)) > 0) {
            level = "SILVER";
        } else {
            level = "MEMBER";
        }

        return new LoyaltyResponseDTO(level);
    }
}
