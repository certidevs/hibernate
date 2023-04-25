package com.example.model;

import jakarta.persistence.*;
import org.hibernate.annotations.*;

@Entity
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String concept;

    @Any
    @AnyDiscriminator(DiscriminatorType.CHAR)
    @AnyDiscriminatorValue(discriminator = "V", entity = Vehicle.class)
    @AnyDiscriminatorValue(discriminator = "H", entity = House.class)
    @AnyKeyJavaClass(Long.class)
    @Column(name = "item_type")
    @JoinColumn(name = "item_id")
    private Item item;

    public Borrow() {
    }

    public Borrow(String concept, Item item) {
        this.concept = concept;
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "id=" + id +
                ", concept='" + concept + '\'' +
                ", item=" + item +
                '}';
    }
}
