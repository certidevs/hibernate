package com.example.joined;

import jakarta.persistence.Entity;

@Entity
public class Phone extends Device {
    private String number;

    public Phone() {
    }

    public Phone(Long id, String manufacturer, DeviceOwner owner, String number) {
        super(id, manufacturer, owner);
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "number='" + number + '\'' +
                ", id=" + id +
                '}';
    }
}

