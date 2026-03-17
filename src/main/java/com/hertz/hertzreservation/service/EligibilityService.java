package com.hertz.hertzreservation.service;

import com.hertz.hertzreservation.dto.EligibilityRequestDTO;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@Service
public class EligibilityService {

    // Logger for eligibility checks
    private static final Logger logger =
            LoggerFactory.getLogger(EligibilityService.class);

    public boolean isEligible(EligibilityRequestDTO request) {

        logger.info("Starting eligibility evaluation for customer with age={}", request.getAge());

        // Rule 1: Age must be >= 18
        if (request.getAge() < 18) {
            logger.warn("Eligibility failed: customer age is below minimum requirement");
            return false;
        }

        logger.debug("Age validation passed");

        // Rule 2: Income OR Rental Count
        boolean incomeEligible =
                request.getAnnualIncome().compareTo(BigDecimal.valueOf(10000)) > 0;

        boolean rentalEligible =
                request.getRentalsLastYearCount() >= 3;

        logger.debug("Income eligibility={}, Rental eligibility={}",
                incomeEligible, rentalEligible);

        if (!(incomeEligible || rentalEligible)) {
            logger.warn("Eligibility failed: neither income nor rental criteria satisfied");
            return false;
        }

        logger.debug("Income or rental validation passed");

        // Rule 3: No accidents in last year
        if (request.getAccidentsLastYearCount() > 0) {
            logger.warn("Eligibility failed: customer has accidents in last year count={}",
                    request.getAccidentsLastYearCount());
            return false;
        }

        logger.info("Customer is eligible for rental product");

        return true;
    }
}