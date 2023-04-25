package com.example;

import com.example.model.Employee;
import com.example.model.ProductOrder;
import org.junit.jupiter.api.Test;

public class SequenceTest {

    @Test
    void identity() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var po1 = session.find(ProductOrder.class, 1L);
        System.out.println(po1);
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var po1 = new ProductOrder("customer1", 30.8);
        var po2 = new ProductOrder("customer1", 30.8);
        var po3 = new ProductOrder("customer1", 30.8);

        session.persist(po1);
        session.persist(po2);
        session.persist(po3);

        session.getTransaction().commit();
    }
}
