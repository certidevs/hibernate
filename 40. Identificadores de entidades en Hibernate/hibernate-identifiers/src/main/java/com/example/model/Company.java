package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(CompanyPK.class)
public class Company {

    @Id
    private String cif;

    @Id
    private String name;

    private Integer employees;

    public Company(String cif, String name, Integer employees) {
        this.cif = cif;
        this.name = name;
        this.employees = employees;
    }
    public Company(CompanyPK pk, Integer employees) {
        this.setId(pk);
        this.employees = employees;
    }

    public Company() {
    }

    public CompanyPK getId(){
        return new CompanyPK(cif, name);
    }

    public void setId(CompanyPK id){
        this.cif = id.getCif();
        this.name = id.getName();
    }

    public Integer getEmployees() {
        return employees;
    }

    public void setEmployees(Integer employees) {
        this.employees = employees;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
