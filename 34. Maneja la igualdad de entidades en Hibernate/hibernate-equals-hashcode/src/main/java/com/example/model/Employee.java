package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NaturalId
    private String dni;

    public Employee() {
    }

    public Employee(String name, String dni) {
        this.name = name;
        this.dni = dni;
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

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object obj) {
//        System.out.println("equals");
        if(this == obj) return true;
        if(obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) return false;
        var emp = (Employee) obj;
        return dni != null && Objects.equals(dni, emp.dni);
    }

    @Override
    public int hashCode() {
//        System.out.println("hashCode");
//        return getClass().hashCode();
        return Objects.hashCode(dni);
    }



}
