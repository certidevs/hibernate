package com.example.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private Double salary;
    private Double bonus;
    private String city;
    private String fullName;

    @ElementCollection
    private List<String> phones = new ArrayList<>();

    private LocalDateTime joinDate;

    public Employee(String email, Double salary, Double bonus, String city, String fullName, List<String> phones, LocalDateTime joinDate) {
        this.email = email;
        this.salary = salary;
        this.bonus = bonus;
        this.city = city;
        this.fullName = fullName;
        this.phones = phones;
        this.joinDate = joinDate;
    }

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", bonus=" + bonus +
                ", city='" + city + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phones=" + phones +
                ", joinDate=" + joinDate +
                '}';
    }
}
