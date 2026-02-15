package com.hertz.hertzreservation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductResponseDTO {

    private Long productId;
    private String category;
    private String size;
    private String type;
    private String driveType;
    private String duration;
    private BigDecimal price;
    private LocalDate validFrom;
    private LocalDate validTo;

    public ProductResponseDTO() {
    }

    public ProductResponseDTO(Long productId,
                              String category,
                              String size,
                              String type,
                              String driveType,
                              String duration,
                              BigDecimal price,
                              LocalDate validFrom,
                              LocalDate validTo) {
        this.productId = productId;
        this.category = category;
        this.size = size;
        this.type = type;
        this.driveType = driveType;
        this.duration = duration;
        this.price = price;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public Long getProductId() {
        return productId;
    }

    public String getCategory() {
        return category;
    }

    public String getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public String getDriveType() {
        return driveType;
    }

    public String getDuration() {
        return duration;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }
}
