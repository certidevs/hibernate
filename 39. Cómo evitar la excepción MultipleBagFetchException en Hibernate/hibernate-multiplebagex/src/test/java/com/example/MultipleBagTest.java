package com.example;

import com.example.model.Company;
import com.example.model.Computer;
import com.example.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MultipleBagTest {


    /*
    org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags:

     No es recomendable cambiar el tipo de colección de una de las asociaciones
     eso puede producir problemas de rendimiento porque realizará el
     producto cartesiano de las tablas involucradas.
     */
    @Test
    @DisplayName("Escenario para reproducir la excepcion MultipleBagException")
    void error() {

        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select c from Company c
                join fetch c.computers
                join fetch c.employees
                where c.id = :id
                """;

        var company1 = session.createQuery(jpql, Company.class)
                .setParameter("id", 1L)
                .getSingleResult();

        company1.getComputers().forEach(System.out::println);
        company1.getEmployees().forEach(System.out::println);
    }

    @Test
    @DisplayName("Escenario solucion para la excepcion MultipleBagException")
    void solution() {

        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        // computers
        String jpql = """
                select c from Company c
                join fetch c.computers
                where c.id = :id
                """;
        var company1 = session.createQuery(jpql, Company.class)
                .setParameter("id", 1L)
                .getSingleResult();
        company1.getComputers().forEach(System.out::println);

        // employees
        jpql = """
                select c from Company c
                join fetch c.employees
                where c.id = :id
                """;
        company1 = session.createQuery(jpql, Company.class)
                .setParameter("id", 1L)
                .getSingleResult();
        company1.getEmployees().forEach(System.out::println);


    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var company1 = new Company("c1");

        session.persist(company1);

        var computer1 = new Computer("Asus", "A55A", company1);
        var computer2 = new Computer("MSI", "Modern", company1);
        var computer3 = new Computer("Lenovo", "XCCC", company1);

        var employee1 = new Employee("emp1", company1);
        var employee2 = new Employee("emp1", company1);
        var employee3 = new Employee("emp1", company1);

        session.persist(computer1);
        session.persist(computer2);
        session.persist(computer3);

        session.persist(employee1);
        session.persist(employee2);
        session.persist(employee3);
        session.getTransaction().commit();

    }
}
