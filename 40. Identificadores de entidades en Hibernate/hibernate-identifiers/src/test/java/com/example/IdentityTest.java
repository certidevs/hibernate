package com.example;

import com.example.model.Employee;
import org.junit.jupiter.api.Test;

public class IdentityTest {

    @Test
    void identity() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var emp = session.find(Employee.class, 1L);
        System.out.println(emp);
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var emp1 = new Employee("emp1");
        var emp2 = new Employee("emp2");
        var emp3 = new Employee("emp3");

        session.persist(emp1);
        session.persist(emp2);
        session.persist(emp3);

        session.getTransaction().commit();
    }
}
