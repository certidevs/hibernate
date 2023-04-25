package com.example.model;

import jakarta.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private Double salary;
    private Double bonus;
    private Integer age;
    private String city;
    private String category;
    private String fullName;

    public Employee() {
    }

    public Employee(String email, Double salary, Double bonus, Integer age, String city, String category, String fullName) {
        this.email = email;
        this.salary = salary;
        this.bonus = bonus;
        this.age = age;
        this.city = city;
        this.category = category;
        this.fullName = fullName;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", bonus=" + bonus +
                ", age=" + age +
                ", city='" + city + '\'' +
                ", category='" + category + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
