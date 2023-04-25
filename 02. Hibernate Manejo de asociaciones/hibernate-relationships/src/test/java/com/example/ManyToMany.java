package com.example;

import com.example.model.Address;
import com.example.model.Author;
import com.example.model.Book;
import com.example.model.Category;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ManyToMany {

    @Test
    void manyToOne() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var book1 = session.find(Book.class, 1L);
        System.out.println(book1.getCategories());

    }

    void insertData(){
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();


        var book1 = new Book("book1", 19.99, 450, true, null);
        var book2 = new Book("book2", 19.99, 450, true, null);
        var book3 = new Book("book3", 19.99, 450, true, null);
        var book4 = new Book("book4", 19.99, 450, true, null);

        var cat1 = new Category("cat1", 16);
        var cat2 = new Category("cat1", 16);
        var cat3 = new Category("cat1", 16);
        var cat4 = new Category("cat1", 16);

        session.persist(cat1);
        session.persist(cat2);
        session.persist(cat3);
        session.persist(cat4);

        book1.getCategories().add(cat1);
        book1.getCategories().add(cat2);

        book2.getCategories().add(cat1);
        book2.getCategories().add(cat3);

        book3.getCategories().add(cat1);


        session.persist(book1);
        session.persist(book2);
        session.persist(book3);
        session.persist(book4);

        session.getTransaction().commit();

    }
}
