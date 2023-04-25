package com.example.model;

import java.io.Serializable;

public class CompanyPK implements Serializable {

    private String cif;
    private String name;

    public CompanyPK() {
    }

    public CompanyPK(String cif, String name) {
        this.cif = cif;
        this.name = name;
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
