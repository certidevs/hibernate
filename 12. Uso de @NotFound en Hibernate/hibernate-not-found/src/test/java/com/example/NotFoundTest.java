package com.example;

import com.example.model.Author;
import com.example.model.Book;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;

public class NotFoundTest {


    @Test
    void notFoundTest() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var book1 = session.find(Book.class, 1L);

        System.out.println("=================");
        System.out.println(book1.getAuthor());
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var author1 = new Author("a1");
        var author2 = new Author("a2");
        session.persist(author1);
        session.persist(author2);

        var book1 = new Book("book1", author1);
        var book2 = new Book("book2", author1);
        var book3 = new Book("book3", author1);

        session.persist(book1);
        session.persist(book2);
        session.persist(book3);

         session.remove(author1);

        session.getTransaction().commit();
    }
}
