package com.hertz.hertzreservation.entity;

import com.hertz.hertzreservation.enums.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

/*
 * VehicleEntity
 *
 * MongoDB document stored in 'vehicles' collection.
 *
 * Design decisions:
 * - Uses Mongo ObjectId as primary identifier.
 * - ENUMs are strongly typed (no raw Strings).
 * - Dates stored as Instant to guarantee UTC/GMT storage.
 * - BigDecimal used for monetary precision.
 */
@Document(collection = "vehicles")
public class VehicleEntity {

    @Id
    private String id; // Mongo ObjectId

    private VehicleCategory category;
    private VehicleSize size;
    private RentalType rentalType;
    private DriveType driveType;
    private DurationType durationType;

    private BigDecimal price;

    /*
     * Stored as UTC.
     * Mongo stores Instant internally in UTC.
     */
    private Instant availableFrom;
    private Instant availableTo;

    public VehicleEntity() {
    }

    public String getId() {
        return id;
    }

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