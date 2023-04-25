package com.example.singletable;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class AccountOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String name;

    @OneToMany(mappedBy = "owner")
    private Set<Account> accounts = new HashSet<>();

    public AccountOwner() {
    }

    public AccountOwner(String name) {
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

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "AccountOwner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
