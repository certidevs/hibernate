package com.example;

import com.example.model.Category;
import com.example.model.Employee;
import com.example.model.Product;
import org.junit.jupiter.api.Test;

public class SQMTest {

    @Test
    void employeeTest_select() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        session.createQuery("select e from Employee e", Employee.class)
                .getResultList()
                .forEach(System.out::println);
    }

    @Test
    void employeeTest_windowFunction() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select e, count(e) over (partition by e.category)
                from Employee e
                """;
        session.createQuery(jpql, Object[].class)
                .getResultList()
                .forEach(objects -> System.out.println(objects[0] + " " + objects[1]));
    }

    @Test
    void productTest() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select p.name, p.category.name, p.unitPrice,
                avg(p.unitPrice) over (partition by p.category)
                from Product p
                """;
        session.createQuery(jpql, Object[].class)
                .getResultList()
                .forEach(objects -> System.out.println(objects[0] + " " + objects[1] + " " + objects[2] + " " + objects[3]));
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var emp1 = new Employee("emp1", "c1");
        var emp2 = new Employee("emp2", "c1");
        var emp3 = new Employee("emp3", "c2");
        var emp4 = new Employee("emp4", "c2");
        var emp5 = new Employee("emp5", "c2");
        var emp6 = new Employee("emp6", "c3");
        session.persist(emp1);
        session.persist(emp2);
        session.persist(emp3);
        session.persist(emp4);
        session.persist(emp5);
        session.persist(emp6);

        var cat1 = new Category("c1");
        var cat2 = new Category("c2");
        var cat3 = new Category("c3");
        session.persist(cat1);
        session.persist(cat2);
        session.persist(cat3);

        var prod1 = new Product("prod1", 9.99, cat1);
        var prod2 = new Product("prod2", 1.99, cat1);
        var prod3 = new Product("prod3", 2.99, cat1);
        var prod4 = new Product("prod4", 3.99, cat2);
        var prod5 = new Product("prod5", 9.99, cat2);
        var prod6 = new Product("prod6", 2.99, cat2);
        var prod7 = new Product("prod7", 8.99, cat2);
        var prod8 = new Product("prod8", 1.99, cat3);
        var prod9 = new Product("prod9", 3.99, cat3);

        session.persist(prod1);
        session.persist(prod2);
        session.persist(prod3);
        session.persist(prod4);
        session.persist(prod5);
        session.persist(prod6);
        session.persist(prod7);
        session.persist(prod8);
        session.persist(prod9);


        session.getTransaction().commit();
        
    }
}
