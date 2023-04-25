package com.example;

import com.example.model.Address;
import com.example.model.Author;
import com.example.model.Book;
import org.junit.jupiter.api.Test;

public class CascadeTest {

    @Test
    void oneToOne() {
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var adr1 = new Address("Calle 1", "Madrid");
        var adr2 = new Address("Calle 2", "Barcelona");
//        session.persist(adr1);
//        session.persist(adr2);

        var a1 = new Author("a1", 20, adr1);
        var a2 = new Author("a2", 20, adr2);

        session.persist(a1);
        session.persist(a2);

        session.getTransaction().commit();

        a1 = session.find(Author.class, 1L);
        System.out.println(a1);
    }

//    @Test
//    void manyToOneBookOwner() {
//        var session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
//
//        var a1 = new Author("a1", 20, null);
////        session.persist(a1);
//
//        var b1 = new Book("b1", a1);
//        var b2 = new Book("b2", a1);
//        var b3 = new Book("b3", a1);
//
//        session.persist(b1);
//        session.persist(b2);
//        session.persist(b3);
//
//        session.getTransaction().commit();
//
//        session.close();
//    }

    // Para descomentar hay que cambiar el owner de Author a Book
//    @Test
//    void oneToManyBookOwnerRemoveAuthor() {
//        var session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
//
//        var a1 = new Author("a1", 20, null);
//        session.persist(a1);
//
//        var b1 = new Book("b1", a1);
//        var b2 = new Book("b2", a1);
//        var b3 = new Book("b3", a1);
//
//        session.persist(b1);
//        session.persist(b2);
//        session.persist(b3);
//
//        session.getTransaction().commit();
//
//        session.close();
//
//        session = HibernateUtil.getSessionFactory().openSession();
//        a1 = session.find(Author.class, 1L);
//
//        session.beginTransaction();
//        // se borran todos los libros
//        session.remove(a1);
//        session.getTransaction().commit();
//        session.close();
//    }

    @Test
    void oneToManyAuthorOwner() {
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        var a1 = new Author("a1", 20, null);


        var b1 = new Book("b1");
        var b2 = new Book("b2");
        var b3 = new Book("b3");

        a1.getBooks().add(b1);
        a1.getBooks().add(b2);
        a1.getBooks().add(b3);

        session.persist(a1);

        session.getTransaction().commit();

        session.close();

    }

    @Test
    void oneToManyAuthorOwnerRemoveAssociation() {
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        var a1 = new Author("a1", 20, null);


        var b1 = new Book("b1");
        var b2 = new Book("b2");
        var b3 = new Book("b3");

        a1.getBooks().add(b1);
        a1.getBooks().add(b2);
        a1.getBooks().add(b3);

        session.persist(a1);

        session.getTransaction().commit();

        session.close();
//
        session = HibernateUtil.getSessionFactory().openSession();
        a1 = session.find(Author.class, 1L);

        session.beginTransaction();

        a1.getBooks().removeIf(book -> book.getTitle().equals("b1"));

        session.persist(a1);

        session.getTransaction().commit();
        session.close();

    }
}
