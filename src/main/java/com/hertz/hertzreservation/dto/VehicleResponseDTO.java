package com.hertz.hertzreservation.dto;

import com.hertz.hertzreservation.enums.*;

import java.math.BigDecimal;
import java.time.Instant;


public class VehicleResponseDTO {

    private String id;

    private VehicleCategory category;
    private VehicleSize size;
    private RentalType rentalType;
    private DriveType driveType;
    private DurationType durationType;

    private BigDecimal price;

    private Instant availableFrom;
    private Instant availableTo;

    public VehicleResponseDTO() {}

    public VehicleResponseDTO(
            String id,
            VehicleCategory category,
            VehicleSize size,
            RentalType rentalType,
            DriveType driveType,
            DurationType durationType,
            BigDecimal price,
            Instant availableFrom,
            Instant availableTo) {

        this.id = id;
        this.category = category;
        this.size = size;
        this.rentalType = rentalType;
        this.driveType = driveType;
        this.durationType = durationType;
        this.price = price;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
    }

    public String getId() {
        return id;
    }

    public VehicleCategory getCategory() {
        return category;
    }

    public VehicleSize getSize() {
        return size;
    }

    public RentalType getRentalType() {
        return rentalType;
    }

    public DriveType getDriveType() {
        return driveType;
    }

    public DurationType getDurationType() {
        return durationType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Instant getAvailableFrom() {
        return availableFrom;
    }

    public Instant getAvailableTo() {
        return availableTo;
    }
}