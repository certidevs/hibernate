package com.example;

import com.example.model.Employee;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

/*
CRUD:
- Create
- Retrieve
- Update
- Delete
 */
public class HibernateTest {

    @Test
    void persist() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var employee1 = new Employee("employee1", 23);
        var employee2 = new Employee("employee2", 25);

        session.persist(employee1);
        session.persist(employee2);

        session.getTransaction().commit();

        session.close();
    }

    @Test
    void retrieve() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var employee1 = new Employee("employee1", 23);
        var employee2 = new Employee("employee2", 25);

        session.persist(employee1);
        session.persist(employee2);
        session.getTransaction().commit();

        var employee1FromDb = session.find(Employee.class, 1L);

        System.out.println(employee1FromDb);

    }


    @Test
    void update() {

        Session session = HibernateUtil.getSessionFactory().openSession();



        var emp1 = new Employee("employee1", 23);
        emp1.setId(3L);
        emp1.setAge(24);

        session.beginTransaction();
        session.merge(emp1);
        session.getTransaction().commit();

        System.out.println(emp1);

    }

    @Test
    void delete() {


        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(session.find(Employee.class, 3L));
        session.getTransaction().commit();
    }
}
