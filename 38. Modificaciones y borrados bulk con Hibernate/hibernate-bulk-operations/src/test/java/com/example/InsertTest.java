package com.example;

import com.example.model.SaleOrder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class InsertTest {

    @Test
    void insert() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        String jpql = """
                insert into SaleOrderHistory (unitQuantity, moneyAmount, deliveryDate)
                select o.unitQuantity, o.moneyAmount, o.deliveryDate
                from SaleOrder o
                where o.deliveryDate < :deliveryDate
                """;

        // session.createQuery(jpql).executeUpdate();

        int inserted = session.createMutationQuery(jpql)
                .setParameter("deliveryDate", LocalDateTime.now())
                .executeUpdate();

        System.out.println("Inserted: " + inserted);

        session.getTransaction().commit();
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        for (int i = 0; i < 10_000; i++)
            session.persist(new SaleOrder(5, 50.0, LocalDateTime.of(2021, 1, 1, 0, 0)));


        session.getTransaction().commit();

    }
}
