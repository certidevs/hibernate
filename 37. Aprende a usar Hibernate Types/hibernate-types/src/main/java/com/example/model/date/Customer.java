package com.example.model.date;

import com.vladmihalcea.hibernate.type.basic.YearMonthIntegerType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.time.YearMonth;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @Type(YearMonthIntegerType.class)
    private YearMonth yearMonth;

    public Customer() {
    }

    public Customer(String name, YearMonth yearMonth) {
        this.name = name;
        this.yearMonth = yearMonth;
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

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", yearMonth=" + yearMonth +
                '}';
    }
}
