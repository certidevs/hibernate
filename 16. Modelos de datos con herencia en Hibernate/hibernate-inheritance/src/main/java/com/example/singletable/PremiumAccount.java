package com.example.singletable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "PremiumAccount")
@DiscriminatorValue("PremiumAccount")
public class PremiumAccount extends Account {

    private Double monthlyBenefit;

    public PremiumAccount() {
    }

    public PremiumAccount(Long id, AccountOwner owner, Double monthlyBenefit) {
        super(id, owner);
        this.monthlyBenefit = monthlyBenefit;
    }

    public Double getMonthlyBenefit() {
        return monthlyBenefit;
    }

    public void setMonthlyBenefit(Double monthlyBenefit) {
        this.monthlyBenefit = monthlyBenefit;
    }

    @Override
    public String toString() {
        return "PremiumAccount{" +
                "monthlyBenefit=" + monthlyBenefit +
                ", id=" + id +
                ", owner=" + owner +
                '}';
    }
}
