package com.hertz.hertzreservation.dto;

/*
 * VehicleEventDTO
 *
 * Event payload sent to Kafka Event Service.
 */
public class VehicleEventDTO {

    private String eventType;
    private String vehicleId;

    // customer name for email greeting
    private String customerName;

    private String customerEmail;
    private String searchCriteria;

    // stored as readable string e.g. "2026-03-18 14:32:05"
    private String eventTime;

    public VehicleEventDTO() {
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
}