package com.hertz.hertzreservation.dto;

import java.math.BigDecimal;

public class LoyaltyRequestDTO {

    private BigDecimal totalPurchaseAmountYearly;

    public LoyaltyRequestDTO() {
    }

    public BigDecimal getTotalPurchaseAmountYearly() {
        return totalPurchaseAmountYearly;
    }

    public void setTotalPurchaseAmountYearly(BigDecimal totalPurchaseAmountYearly) {
        this.totalPurchaseAmountYearly = totalPurchaseAmountYearly;
    }
}
