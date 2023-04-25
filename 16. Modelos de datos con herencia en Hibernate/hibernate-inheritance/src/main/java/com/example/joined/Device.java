package com.example.joined;
import jakarta.persistence.*;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String manufacturer;

    @ManyToOne
    protected DeviceOwner owner;

    public Device() {
    }

    public Device(Long id, String manufacturer, DeviceOwner owner) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public DeviceOwner getOwner() {
        return owner;
    }

    public void setOwner(DeviceOwner owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", manufacturer='" + manufacturer + '\'' +
                ", owner=" + owner +
                '}';
    }
}
