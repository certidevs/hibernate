package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.locationtech.jts.geom.Polygon;

@Entity
public class HouseRental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Integer numberOfRooms;

    private Polygon surface;

    public HouseRental(String description, Integer numberOfRooms, Polygon surface) {
        this.description = description;
        this.numberOfRooms = numberOfRooms;
        this.surface = surface;
    }

    public HouseRental() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Polygon getSurface() {
        return surface;
    }

    public void setSurface(Polygon surface) {
        this.surface = surface;
    }

    @Override
    public String toString() {
        return "HouseRental{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                ", surface=" + surface +
                '}';
    }
}
