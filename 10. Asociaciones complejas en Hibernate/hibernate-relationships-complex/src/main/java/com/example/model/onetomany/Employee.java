package com.example.model.onetomany;

import jakarta.persistence.*;

@Entity
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    @ManyToOne
//    @JoinColumn(name = "company_id")
//    private Company company;

    public Employee() {
    }
    public Employee(String name) {
        this.name = name;
    }
//    public Employee(String name, Company company) {
//        this.name = name;
//        this.company = company;
//    }

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

//    public Company getCompany() {
//        return company;
//    }
//
//    public void setCompany(Company company) {
//        this.company = company;
//    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", company=" + company +
                '}';
    }
}
