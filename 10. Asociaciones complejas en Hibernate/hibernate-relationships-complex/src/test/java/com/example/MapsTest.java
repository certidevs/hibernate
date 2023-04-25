package com.example;

import com.example.model.maps.Author;
import com.example.model.maps.Book;
import org.junit.jupiter.api.Test;

public class MapsTest {

    @Test
    void mapTest() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var a1 = session.find(Author.class, 1L);

        a1.getBooks().forEach((k, v) -> System.out.println(k + " " + v));
    }

    void insertData(){
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        var author1 = new Author("a1");
        var author2 = new Author("a2");
        session.persist(author1);
        session.persist(author2);

        var book1 = new Book("b1", "1111111A", 2.99);
        var book2 = new Book("b2", "2222222B", 5.99);
        var book3 = new Book("b3", "3333333C", 9.99);
        session.persist(book1);
        session.persist(book2);
        session.persist(book3);

        author1.getBooks().put(book1.getIsbn(), book1);
        author1.getBooks().put(book2.getIsbn(), book2);
        author1.getBooks().put(book3.getIsbn(), book3);

        session.persist(author1);


        session.getTransaction().commit();
    }
}
