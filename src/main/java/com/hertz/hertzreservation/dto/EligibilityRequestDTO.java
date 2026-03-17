package com.hertz.hertzreservation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EligibilityRequestDTO {

    private String name;
    private int age;
    private BigDecimal annualIncome;
    private int rentalsLastYearCount;
    
    private int accidentsLastYearCount;
    private LocalDate reservationDate;
    
    
    public EligibilityRequestDTO() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
