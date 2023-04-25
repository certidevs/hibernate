package com.example.joined;

import jakarta.persistence.Entity;

@Entity
public class Computer extends Device {
    private String cpu;

    public Computer() {
    }


    public Computer(Long id, String manufacturer, DeviceOwner owner, String cpu) {
        super(id, manufacturer, owner);
        this.cpu = cpu;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "cpu='" + cpu + '\'' +
                ", id=" + id +
                '}';
    }
}

