package com.hertz.hertzreservation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomerRequestDTO {

    private int age;
    private BigDecimal income;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
    private BigDecimal price;

    public LocalDate getRentalStartDate() {
        return rentalStartDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setRentalStartDate(LocalDate rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public LocalDate getRentalEndDate() {
        return rentalEndDate;
    }

    public void setRentalEndDate(LocalDate rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }

    public CustomerRequestDTO() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}
