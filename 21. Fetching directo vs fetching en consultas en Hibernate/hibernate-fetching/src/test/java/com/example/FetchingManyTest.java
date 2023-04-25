package com.example;

import com.example.model.Address;
import com.example.model.Author;
import com.example.model.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class FetchingManyTest {

    @Test
    void findAuthorById() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select a from Author a
                where a.id = :id
                """;

        var a1 = session.createQuery(jpql, Author.class)
                .setParameter("id", 1L)
                .getSingleResult();

        System.out.println("===============");
        a1.getBooks().forEach(System.out::println);

    }
    @Test
    void findAuthorByIdWithBooks() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select a from Author a
                join fetch a.books
                where a.id = :id
                """;

        var a1 = session.createQuery(jpql, Author.class)
                .setParameter("id", 1L)
                .getSingleResult();

        System.out.println("===============");
        a1.getBooks().forEach(System.out::println);

    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();


        var author1 = new Author("author1", "a1@email.com", LocalDate.of(1990, 1, 1));
        var author2 = new Author("author2", "a2@email.com", LocalDate.of(1990, 1, 1));
        var author3 = new Author("author3", "a3@email.com", LocalDate.of(1990, 1, 1));

        session.beginTransaction();

        session.persist(author1);
        session.persist(author2);
        session.persist(author3);


        var book1 = new Book("b1", 1.0, 100, true, author1);
        var book2 = new Book("b2", 1.0, 100, true, author1);
        var book3 = new Book("b3", 1.0, 100, true, author1);

        session.persist(book1);
        session.persist(book2);
        session.persist(book3);

        session.getTransaction().commit();

    }

}
