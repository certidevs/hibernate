package com.example;

import com.example.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;

// Funciones de agregación de datos
public class AggTest {

    @Test
    void min() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select min(e.age) from Employee e";
        Integer minAge = session.createQuery(jpql, Integer.class).getSingleResult();
        System.out.println(minAge);
    }

    @Test
    void max() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select max(e.salary) from Employee e";
        Double maxSalary = session.createQuery(jpql, Double.class).getSingleResult();
        System.out.println(maxSalary);
    }

    @Test
    void max2() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select max(e.salary + e.bonus) from Employee e";
        Double maxSalaryWithBonus = session.createQuery(jpql, Double.class).getSingleResult();
        System.out.println(maxSalaryWithBonus);
    }

    @Test
    void sum() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        // costes totales para una empresa
        String jpql = "select sum(e.salary) from Employee e";
        Double totalCost = session.createQuery(jpql, Double.class).getSingleResult();
        System.out.println(totalCost);
    }


    @Test
    void avg() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        // edad media en la empresa
        String jpql = "select round(avg(e.age), 0) from Employee e";
        Double avgAge = session.createQuery(jpql, Double.class).getSingleResult();
        System.out.println(avgAge.intValue());
    }

    @Test
    void count() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        // edad media en la empresa
        String jpql = "select count(e.id) from Employee e";
        Long numEmployees = session.createQuery(jpql, Long.class).getSingleResult();
        System.out.println(numEmployees);
    }

    @Test
    void multipleAggs() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select max(e.salary), max(e.bonus), min(e.salary), min(e.bonus) from Employee e";
        Object[] results = session.createQuery(jpql, Object[].class).getSingleResult();

        System.out.println("Max salary: " + results[0]);
        System.out.println("Max bonus: " + results[1]);
        System.out.println("Min salary: " + results[2]);
        System.out.println("Min bonus: " + results[3]);
    }


    @Test
    void groupBy() {

        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select count(e.id), e.city
                from Employee e
                group by e.city
                """;
        List<Object[]> results = session.createQuery(jpql, Object[].class).list();

        results.forEach(r -> System.out.println("City: " + r[1] + ", num employees: " + r[0]));


    }

    @Test
    void groupByHaving() {

        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select count(e.id), e.city
                from Employee e
                group by e.city
                having avg(e.salary) > 25000
                """;
        List<Object[]> results = session.createQuery(jpql, Object[].class).list();

        results.forEach(r -> System.out.println("City: " + r[1] + ", num employees: " + r[0]));


    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var emp1 = new Employee("e1@email.com", 10000.0, 100.0, 22, "Madrid", "JUNIOR", "emp1 emp1");
        var emp2 = new Employee("e2@email.com", 20000.0, 200.0, 23, "Madrid", "SENIOR", "emp1 emp1");
        var emp3 = new Employee("e3@email.com", 30000.0, 300.0, 56, "Madrid", "JUNIOR", "emp1 emp1");
        var emp4 = new Employee("e4@email.com", 40000.0, 400.0, 25, "Barcelona", "JUNIOR", "emp1 emp1");
        var emp5 = new Employee("e5@email.com", 10000.0, 100.0, 26, "Barcelona", "SENIOR", "emp1 emp1");
        var emp6 = new Employee("e6@email.com", 20000.0, 200.0, 27, "Barcelona", "JUNIOR", "emp1 emp1");
        var emp7 = new Employee("e7@email.com", 30000.0, 100000.0, 35, "Gijón", "SENIOR", "emp1 emp1");
        var emp8 = new Employee("e8@email.com", 80000.0, 400.0, 43, "Gijón", "JUNIOR", "emp1 emp1");

        session.persist(emp1);
        session.persist(emp2);
        session.persist(emp3);
        session.persist(emp4);
        session.persist(emp5);
        session.persist(emp6);
        session.persist(emp7);
        session.persist(emp8);

        session.getTransaction().commit();

    }
}
