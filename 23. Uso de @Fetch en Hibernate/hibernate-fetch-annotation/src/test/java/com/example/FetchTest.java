package com.example;

import com.example.model.Author;
import com.example.model.Book;
import com.example.model.Chapter;
import org.junit.jupiter.api.Test;

public class FetchTest {

    @Test
    void withoutFetch() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var a1 = session.find(Author.class, 1L);
        // session.close();
        System.out.println("========");
        System.out.println(a1.getBooks());
        System.out.println("========");
        a1.getBooks().forEach(b -> System.out.println(b.getChapters()));
    }

    // SELECT por defecto es LAZY
    @Test
    void fetch_Select() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var authors = session.createQuery("select a From Author a", Author.class).getResultList();
        // session.close();
        System.out.println("========");
        authors.forEach(a -> System.out.println(a.getBooks()));
    }


    // SUBSELECT por defecto LAZY
    @Test
    void fetch_Subselect() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var authors = session.createQuery("select a From Author a", Author.class).getResultList();
        // session.close();
        System.out.println("========");
        authors.forEach(a -> System.out.println(a.getBooks()));
    }

    // EAGER
    @Test
    void fetch_Join() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var a1 = session.find(Author.class, 1L);
        System.out.println("====");
        System.out.println(a1.getName());
//        a1.getBooks().forEach(b -> System.out.println(b.getChapters()));
    }


    @Test
    void jpql() {

        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select a From Author a
                where a.id = :id
                """;
        var a1 = session.createQuery(jpql, Author.class)
                .setParameter("id", 1L)
                .getSingleResult();

        System.out.println("====");
        System.out.println(a1.getName());

    }

    @Test
    void jpqlWithJoinFetchs() {

        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select a From Author a
                join fetch a.books b
                join fetch b.chapters c
                where a.id = :id
                """;
        var a1 = session.createQuery(jpql, Author.class)
                .setParameter("id", 1L)
                .getSingleResult();

        System.out.println("====");
        System.out.println(a1.getBooks());
        a1.getBooks().forEach(b -> System.out.println(b.getChapters()));

    }

    void insertData(){


        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        var a1 = new Author("a1");
        var a2 = new Author("a1");
        session.persist(a1);
        session.persist(a2);

        var b1 = new Book("b1", 1.0, a1);
        var b2 = new Book("b2", 2.0, a1);
        var b3 = new Book("b3", 3.0, a1);
        session.persist(b1);
        session.persist(b2);
        session.persist(b3);

        var c1 = new Chapter("c1", b1);
        var c2 = new Chapter("c2", b1);
        var c3 = new Chapter("c1", b2);
        var c4 = new Chapter("c2", b2);
        session.persist(c1);
        session.persist(c2);
        session.persist(c3);
        session.persist(c4);

        session.getTransaction().commit();

    }
}
