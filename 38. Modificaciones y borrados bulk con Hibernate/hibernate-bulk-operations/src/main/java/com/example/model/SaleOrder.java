package com.example.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class SaleOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer unitQuantity;

    private Double moneyAmount;

    private LocalDateTime deliveryDate;

    public SaleOrder() {
    }

    public SaleOrder(Integer unitQuantity, Double moneyAmount, LocalDateTime deliveryDate) {
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
        return "SaleOrder{" +
                "id=" + id +
                ", unitQuantity=" + unitQuantity +
                ", moneyAmount=" + moneyAmount +
                ", deliveryDate=" + deliveryDate +
                '}';
    }
}
