package com.example;

import com.example.model.Address;
import com.example.model.Employee;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

public class EntityTest {

    @Test
    void name() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var emp1 = session.find(Employee.class, 1L);
        System.out.println(emp1);
        session.close();
        HibernateUtil.shutdown();
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var employee1 = new Employee("111111", "e1@email.com", "11111", 3000.0,
                20, LocalTime.of(10, 30), LocalTime.of(18, 30));

        var employee2 = new Employee("222222", "e2@email.com", "22222", 3000.0,
                20, LocalTime.of(10, 30), LocalTime.of(18, 30));

        session.persist(employee1);
        session.persist(employee2);

        session.getTransaction().commit();

    }
}
