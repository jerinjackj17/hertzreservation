package com.hertz.hertzreservation.service;

import com.hertz.hertzreservation.dto.EligibilityRequestDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EligibilityService {

    public boolean isEligible(EligibilityRequestDTO request) {

        // Rule 1: Age
        if (request.getAge() < 18) {
            return false;
        }

        // Rule 2: Income OR Rental Count
        boolean incomeEligible =
                request.getAnnualIncome().compareTo(BigDecimal.valueOf(10000)) > 0;

        boolean rentalEligible =
                request.getRentalsLastYearCount() >= 3;

        if (!(incomeEligible || rentalEligible)) {
            return false;
        }

        // Rule 3: No accidents in last 1 year
        if (request.getAccidentsLastYearCount() > 0) {
            return false;
        }

        return true;
    }
}
