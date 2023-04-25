package com.example.mappedsuperclass;


import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Employee extends User {

    private Double salary;
    private LocalDate joinDate;

    public Employee() {
    }

    public Employee(String dni, Double salary, LocalDate joinDate) {
        super(dni);
        this.salary = salary;
        this.joinDate = joinDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "salary=" + salary +
                ", joinDate=" + joinDate +
                ", id=" + id +
                ", dni='" + dni + '\'' +
                '}';
    }
}
