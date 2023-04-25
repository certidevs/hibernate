package com.example;

import com.example.model.Employee;
import jakarta.persistence.Tuple;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FunctionsTest {

    @Test
    void coalesce() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select coalesce(e.fullName, e.email)
                from Employee e
                """;

        var employeeNames = session.createQuery(jpql, String.class).list();
        employeeNames.forEach(System.out::println);

    }

    @Test
    void size() {

        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select e.id as id,
                size(e.phones) as num_phones
                from Employee e
                """;

        List<Tuple> employeePhones = session.createQuery(jpql, Tuple.class).list();

        employeePhones.forEach(
                t -> System.out.println("Employee id " + t.get("id") + ": " + t.get("num_phones"))
        );
    }


    @Test
    void concat() {

        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select e.id as id,
                concat(e.email, ' ', coalesce(e.city, 'Madrid') ) as email_city
                from Employee e
                """;

        List<Tuple> results = session.createQuery(jpql, Tuple.class).list();

        results.forEach(
                t -> System.out.println("Employee id " + t.get("id") + ": " + t.get("email_city"))
        );
    }

    @Test
    void upperLower() {

        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select e.id as id,
                concat(e.email, ' ', upper(coalesce(e.city, 'Madrid')) ) as email_city
                from Employee e
                """;

        List<Tuple> results = session.createQuery(jpql, Tuple.class).list();

        results.forEach(
                t -> System.out.println("Employee id " + t.get("id") + ": " + t.get("email_city"))
        );
    }

    @Test
    void replace() {

        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        // Función de Hibernate
        String hql = """
                select e.id as id,
                replace(e.email, '@company.com', '') as nickname
                from Employee e
                """;

        List<Tuple> results = session.createQuery(hql, Tuple.class).list();

        results.forEach(
                t -> System.out.println("Employee id " + t.get("id") + ": " + t.get("nickname"))
        );
    }

    @Test
    void extract() {

        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        // Función de Hibernate
//        String hql = """
//                select e.id as id,
//                extract(year from e.joinDate) as join_year
//                from Employee e
//                """;
        String hql = """
                select e.id as id,
                year(e.joinDate) as join_year
                from Employee e
                """;

        List<Tuple> results = session.createQuery(hql, Tuple.class).list();

        results.forEach(
                t -> System.out.println("Employee id " + t.get("id") + ": " + t.get("join_year"))
        );

    }

    @Test
    void userFunction() {

        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        // IMPORTANTE: ejecutar primero el SQL en la base de datos para crear la función EmployeeCategory
        String hql = """
                select e.id as id,
                e.salary as salary,
                function('EmployeeCategory', e.salary) as category
                from Employee e
                """;

        List<Tuple> results = session.createQuery(hql, Tuple.class).list();

        results.forEach(
                t -> System.out.println(
                        "Employee id " + t.get("id") + ": "
                                + t.get("salary") + " - " + t.get("category")
                )
        );

    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var phones1 = new ArrayList<>(List.of("1111", "2222"));
        var emp1 = new Employee("john.doe@company.com", 1000.0, 100.0, "barcelona", "emp1", phones1, LocalDateTime.of(2021, 12, 25, 10, 30));

        var phones2 = new ArrayList<>(List.of("1111", "2222"));
        var emp2 = new Employee("bob.app@company.com", 1000.0, 100.0, "gijón", null, phones2, LocalDateTime.of(2021, 12, 25, 10, 30));

        var phones3 = new ArrayList<>(List.of("1111", "2222"));
        var emp3 = new Employee("mike.app@company.com", 1000.0, 100.0, null, "emp3", phones3, LocalDateTime.of(2021, 12, 25, 10, 30));

        session.persist(emp1);
        session.persist(emp2);
        session.persist(emp3);

        session.getTransaction().commit();
    }
}
