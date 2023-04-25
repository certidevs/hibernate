package com.example.model;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NaturalId // por defecto no es mutable
    @Column(nullable = false, unique = true)
    private String dni;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String email;

    public Customer() {
    }

    public Customer(String name, String dni, String email) {
        this.name = name;
        this.dni = dni;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
