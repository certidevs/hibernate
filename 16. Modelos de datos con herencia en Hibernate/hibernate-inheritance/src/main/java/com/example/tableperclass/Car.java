package com.example.tableperclass;

import jakarta.persistence.Entity;

@Entity
public class Car extends Vehicle{

    private Integer doors;

    public Car(Long id, String manufacturer, Owner owner, Integer doors) {
        super(id, manufacturer, owner);
        this.doors = doors;
    }

    public Car() {
    }

    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    @Override
    public String toString() {
        return "Car{" +
                ", doors=" + doors +
                ", id=" + id +
                ", manufacturer='" + manufacturer + '\'' +
                '}';
    }
}
