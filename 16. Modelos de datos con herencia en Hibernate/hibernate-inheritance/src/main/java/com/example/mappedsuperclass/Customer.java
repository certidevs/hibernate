package com.example.mappedsuperclass;

import jakarta.persistence.Entity;

@Entity
public class Customer extends User {
    private String email;
    private String address;
    private String phone;

    public Customer() {
    }

    public Customer(String dni, String email, String address, String phone) {
        super(dni);
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", id=" + id +
                ", dni='" + dni + '\'' +
                '}';
    }
}

