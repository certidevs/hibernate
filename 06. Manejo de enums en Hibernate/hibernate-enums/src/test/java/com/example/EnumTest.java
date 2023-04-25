package com.example;

import com.example.model.Employee;
import com.example.model.EmployeeCategory;
import org.junit.jupiter.api.Test;

public class EnumTest {


    @Test
    void checkEnum() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var emp1 = session.find(Employee.class, 1L);
        System.out.println(emp1);
    }

    @Test
    void findByCategory() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select e from Employee e where e.category = :category";

        session.createQuery(jpql, Employee.class)
                .setParameter("category", EmployeeCategory.MANAGER)
                .getResultList()
                .forEach(System.out::println);

    }


    @Test
    void findByCategoryStatic() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select e from Employee e where e.category = C_LEVEL";

        session.createQuery(jpql, Employee.class)
                .getResultList()
                .forEach(System.out::println);

    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var emp1 = new Employee("111A", "emp1", 20, EmployeeCategory.JUNIOR);
        var emp2 = new Employee("222B", "emp2", 25, EmployeeCategory.SENIOR);
        var emp3 = new Employee("333C", "emp3", 30, EmployeeCategory.MANAGER);
        var emp4 = new Employee("444D", "emp4", 40, EmployeeCategory.C_LEVEL);

        session.persist(emp1);
        session.persist(emp2);
        session.persist(emp3);
        session.persist(emp4);

        session.getTransaction().commit();


    }
}
