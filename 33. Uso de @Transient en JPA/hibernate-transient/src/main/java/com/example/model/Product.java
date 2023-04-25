package com.example.model;

import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    @Transient
    private Double priceWithTaxes;

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Product(String name, Double price, Double priceWithTaxes) {
        this.name = name;
        this.price = price;
        this.priceWithTaxes = priceWithTaxes;
    }

    public Product() {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceWithTaxes() {
        return priceWithTaxes;
    }

    public void setPriceWithTaxes(Double priceWithTaxes) {
        this.priceWithTaxes = priceWithTaxes;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", priceWithTaxes=" + priceWithTaxes +
                '}';
    }
}
