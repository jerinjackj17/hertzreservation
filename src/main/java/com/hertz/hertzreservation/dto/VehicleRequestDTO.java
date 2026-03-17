package com.hertz.hertzreservation.dto;

import com.hertz.hertzreservation.enums.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Instant;


public class VehicleRequestDTO {

    @NotNull(message = "Category is required")
    private VehicleCategory category;

    @NotNull(message = "Size is required")
    private VehicleSize size;

    @NotNull(message = "Rental type is required")
    private RentalType rentalType;

    @NotNull(message = "Drive type is required")
    private DriveType driveType;

    @NotNull(message = "Duration type is required")
    private DurationType durationType;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Available from date is required")
    private Instant availableFrom;

    @NotNull(message = "Available to date is required")
    private Instant availableTo;

    public VehicleRequestDTO() {}

    public VehicleCategory getCategory() {
        return category;
    }

    public void setCategory(VehicleCategory category) {
        this.category = category;
    }

    public VehicleSize getSize() {
        return size;
    }

    public void setSize(VehicleSize size) {
        this.size = size;
    }

    public RentalType getRentalType() {
        return rentalType;
    }

    public void setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;
    }

    public DriveType getDriveType() {
        return driveType;
    }

    public void setDriveType(DriveType driveType) {
        this.driveType = driveType;
    }

    public DurationType getDurationType() {
        return durationType;
    }

    public void setDurationType(DurationType durationType) {
        this.durationType = durationType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Instant getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(Instant availableFrom) {
        this.availableFrom = availableFrom;
    }

    public Instant getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(Instant availableTo) {
        this.availableTo = availableTo;
    }
}