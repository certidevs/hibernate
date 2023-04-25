package com.example;

import com.example.model.Employee;
import com.example.model.SaleOrder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UpdateTest {

    @Test
    void update() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        String jpql = """
                update Employee e
                set e.bonus = 100.0 * (year(current_date) - year(e.joinDate))
                where e.joinDate is not null
                """;

        int inserted = session.createMutationQuery(jpql)
                .executeUpdate();

        System.out.println("Updated: " + inserted);

        session.getTransaction().commit();
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        for (int i = 0; i < 10_000; i++)
            session.persist(new Employee("e"+ i, 1000.0, 0.0, LocalDate.of(2017, 1, 1)));


        session.getTransaction().commit();

    }
}
