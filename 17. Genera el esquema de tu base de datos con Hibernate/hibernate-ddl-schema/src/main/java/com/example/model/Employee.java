package com.example.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalTime;

@Entity
@Table(
        name = "employees",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"dni", "email"})},
        indexes = {
                @Index(columnList = "salary DESC", name = "idx_salary"),
                @Index(columnList = "nss", name = "idx_nss", unique = true),
                @Index(columnList = "check_in_time, check_out_time", name = "idx_check_in_out_time"),
        }
)
@Check(constraints = "check_in_time < check_out_time and salary > 0 and age > 0")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 9)
    private String dni;

    private String email;

    private String nss;

    @Column(precision = 2)
    private Double salary;

    @ColumnDefault("18")
    private Integer age;

    @Column(name = "check_in_time")
    private LocalTime checkIn;

    @Column(name = "check_out_time")
    private LocalTime checkOut;

    public Employee() {
    }

    public Employee(String dni, String email, String nss, Double salary, Integer age, LocalTime checkIn, LocalTime checkOut) {
        this.dni = dni;
        this.email = email;
        this.nss = nss;
        this.salary = salary;
        this.age = age;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", nss='" + nss + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }
}
