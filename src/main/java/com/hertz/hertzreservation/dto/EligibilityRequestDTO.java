package com.hertz.hertzreservation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EligibilityRequestDTO {

    private String name;

    // added — needed to send VEHICLE_NOT_AVAILABLE email when no products found
    private String email;

    private int age;
    private BigDecimal annualIncome;
    private int rentalsLastYearCount;
    private int accidentsLastYearCount;
    private LocalDate reservationDate;

    public EligibilityRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(BigDecimal annualIncome) {
        this.annualIncome = annualIncome;
    }

    public int getRentalsLastYearCount() {
        return rentalsLastYearCount;
    }

    public void setRentalsLastYearCount(int rentalsLastYearCount) {
        this.rentalsLastYearCount = rentalsLastYearCount;
    }

    public int getAccidentsLastYearCount() {
        return accidentsLastYearCount;
    }

    public void setAccidentsLastYearCount(int accidentsLastYearCount) {
        this.accidentsLastYearCount = accidentsLastYearCount;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }
}