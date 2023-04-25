package com.example;

import com.example.model.Address;
import com.example.model.Author;
import com.example.model.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class OneToManyTest {

    @Test
    void oneToMany() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var book1 = session.find(Author.class, 1L);
        System.out.println(book1.getBooks());

    }

    void insertData(){
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var author1 = new Author("author1", "author1@email.com", LocalDate.of(1990, 1, 1));
        var author2 = new Author("author2", "author2@email.com", LocalDate.of(1990, 1, 1));

        var address1 = new Address("street1", "city1", "country1");
        var address2 = new Address("street2", "city2", "country2");

        author1.setAddress(address1);
        author2.setAddress(address2);

        session.persist(address1);
        session.persist(address2);

        session.persist(author1);
        session.persist(author2);

        var book1 = new Book("book1", 19.99, 450, true, author1);
        var book2 = new Book("book2", 19.99, 450, true, author1);
        var book3 = new Book("book3", 19.99, 450, true, author1);
        var book4 = new Book("book4", 19.99, 450, true, author2);

        session.persist(book1);
        session.persist(book2);
        session.persist(book3);
        session.persist(book4);


        session.getTransaction().commit();

    }
}
