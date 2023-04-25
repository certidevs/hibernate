package com.example;

import com.example.model.example1.Address;
import com.example.model.example1.Customer;
import com.example.model.example1.Employee;
import org.junit.jupiter.api.Test;

public class EmbeddableTest {

    @Test
    void embeddedTest() {

        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var c1 = session.find(Customer.class, 1L);
        System.out.println(c1);

        var e1 = session.find(Employee.class, 1L);
        System.out.println(e1);
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var address1 = new Address("s1", "c1", "c1");
        var address2 = new Address("s2", "c2", "c2");

        var cust1 = new Customer("c1", 18, address1);
        var cust2 = new Customer("c2", 18, address2);
        var emp1 = new Employee("e1", 18, address1);
        var emp2 = new Customer("e2", 18, address2);

        session.persist(cust1);
        session.persist(cust2);
        session.persist(emp1);
        session.persist(emp2);

        session.getTransaction().commit();


    }
}
