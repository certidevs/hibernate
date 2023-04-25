package com.example.model.example2;

import jakarta.persistence.*;

@Entity
public class Company {

//    @Id
//    private Long id;
    @EmbeddedId
    @AttributeOverride(name = "cif", column = @Column(name = "company_cif"))
    @AttributeOverride(name = "brand", column = @Column(name = "company_brand"))
    private CompanyPK id;

    private String location;
    private Integer employeeNum;

    public Company() {
    }

    public Company(CompanyPK id, String location, Integer employeeNum) {
        this.id = id;
        this.location = location;
        this.employeeNum = employeeNum;
    }

    public CompanyPK getId() {
        return id;
    }

    public void setId(CompanyPK id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(Integer employeeNum) {
        this.employeeNum = employeeNum;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", employeeNum=" + employeeNum +
                '}';
    }
}
