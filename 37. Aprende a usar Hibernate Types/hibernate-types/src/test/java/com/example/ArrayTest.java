package com.example;

import com.example.model.array.Product;
import com.example.model.date.Customer;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.Arrays;

public class ArrayTest {

    @Test
    void arrayType() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        session.createQuery("select p from Product p", Product.class)
                .getResultList()
                .forEach(System.out::println);

    }


    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();


        var prod1 = new Product("prod1", new String[]{"tag1", "tag2"}, Arrays.asList("cat1", "cat2"));
        var prod2 = new Product("prod2", new String[]{"tag1", "tag2"}, Arrays.asList("cat1", "cat2"));

        session.persist(prod1);
        session.persist(prod2);

        session.getTransaction().commit();

    }
}
