package com.example;

import com.example.model.Customer;
import com.example.model.Employee;
import org.junit.jupiter.api.Test;

public class NaturalIdTest {

    @Test
    void employeeTest() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

//        var emp1 = session.find(Employee.class, 1L);
//        System.out.println(emp1);

//        var emp1 = session.byNaturalId(Employee.class)
//                .using("dni", "1111A")
//                .load();

        // caso válido cuando únicamente hay una sola clave natural
        var emp2 = session.bySimpleNaturalId(Employee.class).load("2222B");
        System.out.println(emp2);
    }


    @Test
    void customerTest() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var cust1 = session.byNaturalId(Customer.class)
                .using("dni", "1111A")
                .using("email", "cust1@email.com")
                .load();
        System.out.println(cust1);

    }

    @Test
    void multiple() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        session.byMultipleIds(Employee.class)
                .multiLoad(1L, 3L)
                .forEach(System.out::println);

        // org.hibernate.NotYetImplementedFor6Exception: org.hibernate.loader.ast.internal.MultiNaturalIdLoaderStandard
//        session.byMultipleNaturalId(Employee.class)
//                .multiLoad("1111A", "3333C")
//                .forEach(System.out::println);
    }

    void insertData() {
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var emp1 = new Employee("1111A", "david",3000.0);
        var emp2 = new Employee("2222B", "david",4000.0);
        var emp3 = new Employee("3333C", "david",5000.0);

        session.persist(emp1);
        session.persist(emp2);
        session.persist(emp3);

        var cust1 = new Customer("cust1", "1111A", "cust1@email.com");
        var cust2 = new Customer("cust2", "2222A", "cust2@email.com");
        var cust3 = new Customer("cust3", "3333A", "cust3@email.com");
        session.persist(cust1);
        session.persist(cust2);
        session.persist(cust3);
        session.getTransaction().commit();
    }

}
