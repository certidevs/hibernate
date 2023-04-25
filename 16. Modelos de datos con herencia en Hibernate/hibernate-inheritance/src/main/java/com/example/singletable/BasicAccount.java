package com.example.singletable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "BasicAccount")
@DiscriminatorValue("BasicAccount")
public class BasicAccount extends Account {

    private Integer maxTransactions;

    public BasicAccount() {
    }

    public BasicAccount(Long id, AccountOwner owner, Integer maxTransactions) {
        super(id, owner);
        this.maxTransactions = maxTransactions;
    }

    public BasicAccount(Integer maxTransactions) {
        this.maxTransactions = maxTransactions;
    }

    public Integer getMaxTransactions() {
        return maxTransactions;
    }

    public void setMaxTransactions(Integer maxTransactions) {
        this.maxTransactions = maxTransactions;
    }


    @Override
    public String toString() {
        return "BasicAccount{" +
                "maxTransactions=" + maxTransactions +
                ", id=" + id +
                ", owner=" + owner +
                '}';
    }
}
