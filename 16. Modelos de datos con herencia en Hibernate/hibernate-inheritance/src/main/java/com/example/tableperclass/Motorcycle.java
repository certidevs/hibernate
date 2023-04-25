package com.example.tableperclass;

import jakarta.persistence.Entity;

@Entity
public class Motorcycle extends Vehicle{

    private Boolean hasCopilot;

    public Motorcycle(Long id, String manufacturer, Owner owner, Boolean hasCopilot) {
        super(id, manufacturer, owner);
        this.hasCopilot = hasCopilot;
    }

    public Motorcycle() {
    }

    public Boolean getHasCopilot() {
        return hasCopilot;
    }

    public void setHasCopilot(Boolean hasCopilot) {
        this.hasCopilot = hasCopilot;
    }

    @Override
    public String toString() {
        return "Motorcycle{" +
                "hasCopilot=" + hasCopilot +
                ", id=" + id +
                ", manufacturer='" + manufacturer + '\'' +
                '}';
    }
}
