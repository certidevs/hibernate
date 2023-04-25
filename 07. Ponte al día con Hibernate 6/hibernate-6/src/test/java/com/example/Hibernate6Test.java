package com.example;

import com.example.model.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;

public class Hibernate6Test {

    @Test
    void name() {

        Session session = HibernateUtil.getSessionFactory().openSession();

//        session.createQuery("select e from Employee e");
        Query<Employee> query = session.createQuery("select e from Employee e", Employee.class);
    }
}
