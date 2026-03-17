package com.hertz.hertzreservation.dto;

public class BirthdayEventDTO {

    private String eventType;
    private String customerName;
    private String companyName;
    private String email;
    private String eventTime;

    public BirthdayEventDTO() {}

    public BirthdayEventDTO(String eventType,
                            String customerName,
                            String companyName,
                            String email,
                            String eventTime) {
        this.eventType = eventType;
        this.customerName = customerName;
        this.companyName = companyName;
        this.email = email;
        this.eventTime = eventTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
}