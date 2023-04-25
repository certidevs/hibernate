package com.example.model;

import jakarta.persistence.*;

@Entity
public class House implements Item<House>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    private Double price;

    public House() {
    }

    public House(Long id, String location, Double price) {
        this.id = id;
        this.location = location;
        this.price = price;
    }

    @Override
    public House getValue() {
        return new House(this.id, this.location, this.price);
    }

    @Override
    public String getName() {
        return this.location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", price=" + price +
                '}';
    }
}
