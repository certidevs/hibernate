package com.example.model;

import jakarta.persistence.*;


@Entity
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "p_order_sequence")
    @SequenceGenerator(name="p_order_sequence", sequenceName = "p_order_sequence",
            allocationSize = 100)
    private Long id;

    private String name;

    private Double totalPrice;

    public ProductOrder(String name, Double totalPrice) {
        this.name = name;
        this.totalPrice = totalPrice;
    }

    public ProductOrder() {
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
