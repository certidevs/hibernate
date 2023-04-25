package com.example;

import com.example.model.array.Product;
import com.example.model.image.Book;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class ImageTest {

    @Test
    void imageType() throws IOException {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        session.createQuery("select b from Book b", Book.class)
                .getResultList()
                .forEach(System.out::println);

    }


    void insertData() throws IOException {

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();


        byte[] image = Files.readAllBytes(Paths.get("src\\main\\resources\\book.jpg"));
        var book1 = new Book("book 1", image);
        session.persist(book1);


        session.getTransaction().commit();

    }
}
