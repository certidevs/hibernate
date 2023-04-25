package com.example;

import com.example.model.date.Customer;
import com.example.model.json.Employee;
import com.example.model.json.EmployeeDetails;
import jakarta.persistence.Tuple;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.List;

public class DateTest {

    @Test
    void dateType() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        session.createQuery("select c from Customer c", Customer.class)
                .getResultList()
                .forEach(System.out::println);

    }


    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();


        var c1 = new Customer("c1", YearMonth.of(2021, 8));
        var c2 = new Customer("c2", YearMonth.of(2021, 8));

        session.persist(c1);
        session.persist(c2);

        session.getTransaction().commit();

    }
}
