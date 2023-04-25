package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class SaleOrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer unitQuantity;

    private Double moneyAmount;

    private LocalDateTime deliveryDate;

    public SaleOrderHistory() {
    }

    public SaleOrderHistory(Integer unitQuantity, Double moneyAmount, LocalDateTime deliveryDate) {
        this.unitQuantity = unitQuantity;
        this.moneyAmount = moneyAmount;
        this.deliveryDate = deliveryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(Integer unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "SaleOrderHistory{" +
                "id=" + id +
                ", unitQuantity=" + unitQuantity +
                ", moneyAmount=" + moneyAmount +
                ", deliveryDate=" + deliveryDate +
                '}';
    }
}
