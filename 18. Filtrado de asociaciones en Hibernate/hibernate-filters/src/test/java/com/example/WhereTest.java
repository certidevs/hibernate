package com.example;

import com.example.where.Author;
import com.example.where.Book;
import org.junit.jupiter.api.Test;

public class WhereTest {

    @Test
    void name() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var author1 = session.find(Author.class, 1L);
        author1.getBooks().forEach(System.out::println);

//        String jpql = "select a from Author a join fetch a.books where a.id = 1";
//        session.createQuery(jpql, Author.class).getSingleResult().getBooks().forEach(System.out::println);

    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var author1 = new Author("author1");
        var author2 = new Author("author2");

        var book1 = new Book("book1", "comedy", author1);
        var book2 = new Book("book2", "action", author1);
        var book3 = new Book("book3", "scifi", author1);
        var book4 = new Book("book4", "comedy", author1);

        session.persist(author1);
        session.persist(author2);

        session.persist(book1);
        session.persist(book2);
        session.persist(book3);
        session.persist(book4);

        session.getTransaction().commit();

    }
}
