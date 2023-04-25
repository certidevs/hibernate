package com.example;

import com.example.model.Customer;
import com.example.model.ProductOrder;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UUIDTest {

    @Test
    void identity() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select c from Customer c";
        var customers = session.createQuery(jpql, Customer.class)
                .list();

        UUID uuid = customers.get(0).getId();

        var c1 = session.find(Customer.class, uuid);
        System.out.println(c1);
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var c1 = new Customer("customer1", 30);
        var c2 = new Customer("customer2", 50);
        var c3 = new Customer("customer3", 60);

        session.persist(c1);
        session.persist(c2);
        session.persist(c3);

        session.getTransaction().commit();
    }
}
