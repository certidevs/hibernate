package com.example;

import com.example.dto.EmployeeSalary;
import com.example.model.Address;
import com.example.model.Employee;
import jakarta.persistence.Tuple;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NativeSQLTest {

    @Test
    void findAllTest() {
        insertData();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "select * from employees";
        var employees = session.createNativeQuery(sql, Employee.class).list();
        employees.forEach(System.out::println);
    }

    @Test
    void joinTest() {
        insertData();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = """
                    select e.id, e.salary, a.city from employees e
                    LEFT JOIN address a
                    on e.address_id = a.id
                    """;
//        List<Object[]> employees = session.createNativeQuery(sql).list();
//        employees.forEach(e -> System.out.println(e[0] + " " + e[1] + " " + e[2]));

        List<Tuple> employees = session.createNativeQuery(sql, Tuple.class).list();
        employees.forEach(e -> System.out.println(e.get("id") + " " + e.get("salary") + " " + e.get("city")));
    }


    @Test
    void addScalarTest() {
        insertData();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = """
                    select e.id, e.email, e.bonus, e.city from employees e
                    """;
        List<Tuple> employees = session.createNativeQuery(sql, Tuple.class)
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("email", StandardBasicTypes.STRING)
                .addScalar("bonus", StandardBasicTypes.DOUBLE)
                .addScalar("city", StandardBasicTypes.STRING)
                .list();
        employees.forEach(e -> {
            System.out.println(e.get("id"));
            System.out.println(e.get("email"));
            System.out.println(e.get("bonus"));
            System.out.println(e.get("city"));
        });

    }

    @Test
    void namedNativeTuple() {
        insertData();
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.createNamedQuery("find_employee_emails", Tuple.class)
                .list()
                .forEach(e -> System.out.println(e.get("email")));
    }

    @Test
    void namedNativeDTO() {
        insertData();
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.createNamedQuery("find_employee_salaries", EmployeeSalary.class)
                .list()
                .forEach(e -> {
                    System.out.println(e.id());
                    System.out.println(e.salary());
                    System.out.println(e.bonus());
                });
    }


    void insertData(){
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var address1 = new Address("calle1", "c1");
        var address2 = new Address("calle2", "c2");
        var address3 = new Address("calle3", "c3");
        session.persist(address1);
        session.persist(address2);
        session.persist(address3);

        var phones1 = new ArrayList<>(List.of("11", "22"));
        var emp1 = new Employee("e1@com", 1000.0, 100.0, "c1", "e1 e1", phones1,
                LocalDateTime.of(2021, 1, 1, 1, 1), address1);

        var phones2 = new ArrayList<>(List.of("11", "22"));
        var emp2 = new Employee("e2@com", 1000.0, 100.0, "c1", "e1 e1", phones2,
                LocalDateTime.of(2021, 1, 1, 1, 1), address2);

        var phones3 = new ArrayList<>(List.of("11", "22"));
        var emp3 = new Employee("e3@com", 1000.0, 100.0, "c1", "e1 e1", phones3,
                LocalDateTime.of(2021, 1, 1, 1, 1), address3);

        session.persist(emp1);
        session.persist(emp2);
        session.persist(emp3);

        session.getTransaction().commit();
    }
}
