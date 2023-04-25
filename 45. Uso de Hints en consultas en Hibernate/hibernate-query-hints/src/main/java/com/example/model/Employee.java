package com.example.model;

import jakarta.persistence.*;
import org.hibernate.jpa.HibernateHints;


import java.util.HashSet;
import java.util.Set;


@NamedQuery(
        name = "find_all_employees",
        query = "select e FROM Employee e",
        hints = {
                @QueryHint(
                        name = HibernateHints.HINT_READ_ONLY,
                        value = "true"
                )
        }
)
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "employee")
    private Set<Car> cars = new HashSet<>();

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
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

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
