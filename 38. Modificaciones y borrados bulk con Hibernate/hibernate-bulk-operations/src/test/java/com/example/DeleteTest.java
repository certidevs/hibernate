package com.example;

import com.example.model.Customer;
import com.example.model.Employee;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DeleteTest {


    @Test
    void delete() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        String jpql = """
                delete from Customer c
                where c.lastLogin < :lastLogin
                """;

        int inserted = session.createMutationQuery(jpql)
                .setParameter("lastLogin", LocalDateTime.now().minusYears(1))
                .executeUpdate();

        System.out.println("Deleted: " + inserted);

        session.getTransaction().commit();
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        for (int i = 0; i < 10_000; i++)
            session.persist(new Customer("c" + i, LocalDateTime.of(2021, 1, 1, 10, 30)));


        session.getTransaction().commit();

    }
}
