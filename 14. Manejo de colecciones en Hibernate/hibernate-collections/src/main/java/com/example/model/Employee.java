package com.example.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nss;

    @ElementCollection
    private List<String> phones = new ArrayList<>();

    @ElementCollection
    private List<Double> salaries = new ArrayList<>();

    @ElementCollection
    private Set<String> postalCodes = new HashSet<>();

    @OneToMany // un empleado tiene multiples tarjetas, pero una tarjeta pertenece a un solo empleado
    @JoinTable(name = "employee_cards",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id"))
    @MapKeyColumn(name = "c_number")
//    @MapKey(name = "number")
    private Map<String, CreditCard> cards = new HashMap<>();

    public Employee() {
    }

    public Employee(String name, String nss, List<String> phones) {
        this.name = name;
        this.nss = nss;
        this.phones = new ArrayList<>(phones);
    }

    public Employee(String name, String nss, List<String> phones, List<Double> salaries) {
        this.name = name;
        this.nss = nss;
        this.phones = new ArrayList<>(phones);
        this.salaries = new ArrayList<>(salaries);
    }

    public Employee(String name, String nss, List<String> phones, List<Double> salaries, Set<String> postalCodes) {
        this.name = name;
        this.nss = nss;
        this.phones = new ArrayList<>(phones);
        this.salaries = new ArrayList<>(salaries);
        this.postalCodes = new HashSet<>(postalCodes);
    }

    public Employee(String name, String nss, Map<String, CreditCard> cards) {
        this.name = name;
        this.nss = nss;
        this.cards = new HashMap<>(cards);
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

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<Double> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Double> salaries) {
        this.salaries = salaries;
    }

    public Set<String> getPostalCodes() {
        return postalCodes;
    }

    public void setPostalCodes(Set<String> postalCodes) {
        this.postalCodes = postalCodes;
    }

    public Map<String, CreditCard> getCards() {
        return cards;
    }

    public void setCards(Map<String, CreditCard> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nss='" + nss + '\'' +
                ", phones=" + phones +
                '}';
    }
}
