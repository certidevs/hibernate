package com.example;

import com.example.model.CreditCard;
import com.example.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionsTest {

    @Test
    void basicTypes() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var emp1 = session.find(Employee.class, 1L);

        System.out.println(emp1.getPhones());

    }

    @Test
    void basicTypesSalaries() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var emp3 = session.find(Employee.class, 3L);

        System.out.println(emp3.getPhones());
        emp3.getSalaries().stream().reduce(Double::sum).ifPresent(System.out::println);

    }
    @Test
    void basicPostalCodes() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var emp5 = session.find(Employee.class, 5L);

        System.out.println(emp5.getPhones());
        System.out.println(emp5.getSalaries());
        System.out.println(emp5.getPostalCodes());

    }

    @Test
    void entityType() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var emp6 = session.find(Employee.class, 6L);

        emp6.getCards().forEach((k,v) -> System.out.println(k + " " + v));
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var emp1 = new Employee("emp1", "1111", List.of("11111", "22222", "33333"));
        var emp2 = new Employee("emp2", "2222", List.of("33333", "44444", "55555"));

        session.persist(emp1);
        session.persist(emp2);


        var emp3 = new Employee("emp3", "1111", List.of("11111", "22222", "33333"), List.of(1200.0, 1500.0, 2000.0));
        var emp4 = new Employee("emp4", "2222", List.of("33333", "44444", "55555"), List.of(1200.0, 1500.0, 2000.0));

        session.persist(emp3);
        session.persist(emp4);

        var emp5 = new Employee("emp5", "2222",
                List.of("33333", "44444", "55555"),
                List.of(1200.0, 1500.0, 2000.0),
                Set.of("24000", "25000", "26000")
        );
        session.persist(emp5);

        var c1 = new CreditCard("1111", "emp1");
        var c2 = new CreditCard("2222", "emp2");
        var c3 = new CreditCard("3333", "emp3");
        session.persist(c1);
        session.persist(c2);
        session.persist(c3);



        var emp6 = new Employee("emp6", "2222",
                Map.of("1111", c1,
                        "2222", c2,
                        "3333", c3
                )
        );
        session.persist(emp6);


        session.getTransaction().commit();

    }
}
