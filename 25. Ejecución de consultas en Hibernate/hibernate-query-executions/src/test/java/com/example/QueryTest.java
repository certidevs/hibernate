package com.example;

import com.example.model.Employee;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;

public class QueryTest {


    @Test
    void list_And_GetResultList() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select e from Employee e";
        Query<Employee> query = session.createQuery(jpql, Employee.class);
        query.getResultList().forEach(System.out::println);

        System.out.println("======");

        query.list().forEach(System.out::println);
    }

    @Test
    void getResultStream() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select e from Employee e";
        Query<Employee> query = session.createQuery(jpql, Employee.class);
        var emails = query.getResultList().stream()
                .map(Employee::getEmail)
                .map(String::toUpperCase)
                .toList();
        System.out.println(emails);

        System.out.println("======");

        query.getResultStream()
                .map(Employee::getEmail)
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }

    @Test
    void getSingleResult() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select e from Employee e where e.id = :id";
        Query<Employee> query = session.createQuery(jpql, Employee.class);

        /* Opción 1:
            En caso de que no haya un valor:
            NonUniqueResultException – if there is more than one matching result
            NoResultException – if there is no result to return
         */
        Employee emp = query.setParameter("id", 1L).getSingleResult();
        System.out.println(emp);
        System.out.println("=======");

        /* Opción 2:
            En caso de que no haya un valor:
            NonUniqueResultException – if there is more than one matching result
         */
        emp = query.setParameter("id", 1L).getSingleResultOrNull();
        System.out.println(emp);
        System.out.println("=======");
        /*
        Opción 3:
         */

        emp = query.setParameter("id", 1L).uniqueResult();
        System.out.println(emp);
        System.out.println("=======");
        /*
        Opción 4:
         */

        query.setParameter("id", 1L)
                .uniqueResultOptional()
                .map(Employee::getEmail)
                .map(String::toUpperCase)
                .ifPresent(System.out::println);
    }

    @Test
    void limit() {

        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select e from Employee e";
        Query<Employee> query = session.createQuery(jpql, Employee.class);
        query.setMaxResults(2);

        query.getResultList().forEach(System.out::println);

    }

    @Test
    void pagination() {

        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select e from Employee e";
        Query<Employee> query = session.createQuery(jpql, Employee.class);
        query.setFirstResult(2);
        query.setMaxResults(2);

        query.getResultList().forEach(System.out::println);

    }


    @Test
    void dynamicPaginationLastPage() {

        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        // size
        int pageSize = 20;

        // count
        String jpql = "select count(e.id) from Employee e";
        Long numEmployees = session.createQuery(jpql, Long.class).uniqueResult();

        // calc page
        int lastPageNum = (int) Math.ceil(numEmployees / (double) pageSize);

        // pagination
        String empJpql = "select e from Employee e";
        Query<Employee> query = session.createQuery(empJpql, Employee.class);
        int position = (lastPageNum - 1) * pageSize;
        query.setFirstResult(position);
        query.setMaxResults(pageSize);

        System.out.println("lastPageNum: " + lastPageNum);
        System.out.println("position: " + position);
        System.out.println("pageSize: " + pageSize);
        query.getResultList().forEach(System.out::println);

    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        for(int i = 0; i < 100; i++){
            var emp = new Employee(null, "e" + i + "@email.com", 1000.0);
            session.persist(emp);
        }

        session.getTransaction().commit();

    }
}
