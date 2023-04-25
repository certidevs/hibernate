package com.example;

import com.example.mappedsuperclass.Customer;
import com.example.mappedsuperclass.Employee;
import com.example.mappedsuperclass.User;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class MappedSuperClass {

    /*
     Mapped super class es el enfoque más sencillo, se crean dos tablas, una para cada clase hija.
     La clase base no tiene tabla, no permite polimorfismo en asociaciones:
     Company no puede tener como atributo Set<User> users
      */
    @Test
    void mappedSuperClass() {
        insertDemoData();
        Session session = HibernateUtil.getSessionFactory().openSession();

//        var emps = session.createQuery("from Employee", Employee.class).list();
//        emps.forEach(System.out::println);

//        var customers = session.createQuery("from Customer", Customer.class).list();
//        customers.forEach(System.out::println);

        List<User> users = session.createQuery("from Customer", User.class).list();
        users.forEach(System.out::println);
    }

    private void insertDemoData() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        // mapped super class
        var employee1 = new Employee("7777", 1999.9, LocalDate.now());
        var employee2 = new Employee("7777", 1999.9, LocalDate.now());
        var customer1 = new Customer("777", "email", "street", "666");
        var customer2 = new Customer("777", "email", "street", "666");

        session.persist(employee1);
        session.persist(employee2);
        session.persist(customer1);
        session.persist(customer2);

        session.getTransaction().commit();
        session.close();
    }

}
