package com.example;

import com.example.model.Author;
import com.example.model.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class CriteriaTest {

    @Test
    void findAll() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var builder = session.getCriteriaBuilder();
        var criteria = builder.createQuery(Author.class);

        criteria.select(criteria.from(Author.class));

        var authors = session.createQuery(criteria).list();

        authors.forEach(System.out::println);
    }

    @Test
    void findById() {

        insertData();
        Session session = HibernateUtil.getSessionFactory().openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        JpaCriteriaQuery<Author> criteria = builder.createQuery(Author.class);
        JpaRoot<Author> authors = criteria.from(Author.class);

        criteria.select(authors)
                .where(builder.equal(authors.get("id"), 1L));

        Author author = session.createQuery(criteria).getSingleResult();

        System.out.println(author);

    }

    @Test
    void findByEmailLike() {

        insertData();
        Session session = HibernateUtil.getSessionFactory().openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        JpaCriteriaQuery<Author> criteria = builder.createQuery(Author.class);
        JpaRoot<Author> authors = criteria.from(Author.class);

        criteria.select(authors)
                .where(builder.like(authors.get("email"), "%company2.com"));

        var authorsFromDB = session.createQuery(criteria).getResultList();

        authorsFromDB.forEach(System.out::println);

    }

    @Test
    void findByPriceGreaterThan() {

        insertData();
        Session session = HibernateUtil.getSessionFactory().openSession();

        var builder = session.getCriteriaBuilder();
        var criteria = builder.createQuery(Book.class);
        var root = criteria.from(Book.class);

        criteria.select(root)
                .where(builder.greaterThanOrEqualTo(root.get("price"), 29.99));

        var result = session.createQuery(criteria).getResultList();

        result.forEach(System.out::println);

    }

    @Test
    void findBetween() {


        insertData();
        Session session = HibernateUtil.getSessionFactory().openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        JpaCriteriaQuery<Author> criteria = builder.createQuery(Author.class);
        JpaRoot<Author> authors = criteria.from(Author.class);

        criteria.select(authors)
                .where(builder.between(
                        authors.get("birthDate"),
                        LocalDate.of(1989, 1, 1),
                        LocalDate.of(1995, 12, 1)
                    )
                );

        var authorsFromDB = session.createQuery(criteria).getResultList();

        authorsFromDB.forEach(System.out::println);

    }


    @Test
    void findByMultipleWhere() {

        insertData();
        Session session = HibernateUtil.getSessionFactory().openSession();

        var builder = session.getCriteriaBuilder();
        var criteria = builder.createQuery(Book.class);
        var root = criteria.from(Book.class);

        var priceGte30 = builder.greaterThanOrEqualTo(root.get("price"), 29.99);
        var numPagesLte400 = builder.lessThanOrEqualTo(root.get("numPages"), 400);

        criteria.select(root)
                .where(builder.and(priceGte30, numPagesLte400));

        var result = session.createQuery(criteria).getResultList();

        result.forEach(System.out::println);

    }

    @Test
    void multiSelect() {

        insertData();
        Session session = HibernateUtil.getSessionFactory().openSession();

        var builder = session.getCriteriaBuilder();
        var criteria = builder.createQuery(Object[].class);
        var root = criteria.from(Book.class);

        criteria.multiselect(root.get("title"), root.get("price"));

        List<Object[]> results = session.createQuery(criteria).getResultList();

        for(Object[] result : results) {
            System.out.println("Title: " + result[0] + " Price: " + result[1]);
        }

    }




    void insertData(){
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var author1 = new Author("author1", "a1@company1.com", LocalDate.of(1990, 1, 1));
        var author2 = new Author("author2", "a2@company1.com", LocalDate.of(1995, 1, 1));
        var author3 = new Author("author3", "a3@company2.com", LocalDate.of(1996, 1, 1));
        var author4 = new Author("author4", "a4@company2.com", LocalDate.of(1998, 1, 1));

        session.persist(author1);
        session.persist(author2);
        session.persist(author3);
        session.persist(author4);

        var book1 = new Book("book1", 19.99, 450, true, author1);
        var book2 = new Book("book1", 29.99, 450, true, author1);
        var book3 = new Book("book1", 39.99, 350, true, author1);
        session.persist(book1);
        session.persist(book2);
        session.persist(book3);

        session.getTransaction().commit();

    }





}
