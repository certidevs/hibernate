package com.example;

import com.example.model.Author;
import com.example.model.Book;
import com.example.model.Chapter;
import jakarta.persistence.EntityGraph;
import org.hibernate.graph.GraphParser;
import org.junit.jupiter.api.Test;

public class EntityGraphTest {

    @Test
    void entityGraph_1level() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        EntityGraph<Author> graph = session.createEntityGraph(Author.class);
        graph.addAttributeNodes("books");

        session.createQuery("select a from Author a", Author.class)
//                .setHint("javax.persistence.fetchgraph", graph)
                .setHint("jakarta.persistence.fetchgraph", graph)
                .list()
                .forEach(author -> System.out.println(author.getBooks().size()));
    }


    @Test
    void entityGraph_2level() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        EntityGraph<Author> graph = session.createEntityGraph(Author.class);
        graph.addAttributeNodes("books");
        graph.addSubgraph("books").addAttributeNodes("chapters");

        session.createQuery("select a from Author a", Author.class)
                .setHint("jakarta.persistence.fetchgraph", graph)
                .list()
                .forEach(author -> author.getBooks().forEach(
                        book -> System.out.println(book.getChapters().size())
                        )
                );
    }

    @Test
    void entityGraph_2level_GraphExpression() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        EntityGraph<Author> graph =
                GraphParser.parse(Author.class, "books(chapters)", session);

        session.createQuery("select a from Author a", Author.class)
                .setHint("jakarta.persistence.fetchgraph", graph)
                .list()
                .forEach(author -> author.getBooks().forEach(
                                book -> System.out.println(book.getChapters().size())
                        )
                );
    }


    @Test
    void entityGraph_1level_NamedQuery() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var graph = session.createEntityGraph("authorWithBooks");

        session.createQuery("select a from Author a", Author.class)
                .setHint("jakarta.persistence.fetchgraph", graph)
                .list()
                .forEach(author -> System.out.println(author.getBooks().size()));
    }

    @Test
    void entityGraph_2level_NamedQuery() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var graph = session.createEntityGraph("authorWithBooksAndChapters");

        session.createQuery("select a from Author a", Author.class)
                .setHint("jakarta.persistence.fetchgraph", graph)
                .list()
                .forEach(author -> author.getBooks().forEach(
                                book -> System.out.println(book.getChapters().size())
                        )
                );
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        var a1 = new Author("a1");
        var a2 = new Author("a1");
        session.persist(a1);
        session.persist(a2);

        var b1 = new Book("b1", a1);
        var b2 = new Book("b2", a1);
        var b3 = new Book("b3", a2);
        var b4 = new Book("b4", a2);
        session.persist(b1);
        session.persist(b2);
        session.persist(b3);
        session.persist(b4);

        var ch1 = new Chapter("ch1", b1);
        var ch2 = new Chapter("ch2", b1);
        var ch3 = new Chapter("ch3", b2);
        var ch4 = new Chapter("ch4", b2);
        var ch5 = new Chapter("ch5", b3);
        var ch6 = new Chapter("ch6", b3);
        var ch7 = new Chapter("ch7", b4);
        var ch8 = new Chapter("ch8", b4);
        session.persist(ch1);
        session.persist(ch2);
        session.persist(ch3);
        session.persist(ch4);
        session.persist(ch5);
        session.persist(ch6);
        session.persist(ch7);
        session.persist(ch8);

        session.getTransaction().commit();


    }
}
