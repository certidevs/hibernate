package com.example;

import com.example.model.Address;
import com.example.model.Author;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class QueriesTest {


    @Test
    void findAll() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "SELECT a FROM Author a";

        var query = session.createQuery(jpql, Author.class);
        query.list().forEach(System.out::println);

    }

    @Test
    void findByEmail() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "SELECT a FROM Author a WHERE a.email = :email";

        var query = session.createQuery(jpql, Author.class);
        query.setParameter("email", "a2@email.com");

        var author = query.getSingleResult();
        System.out.println(author);

    }

    @Test
    void findByDates() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "SELECT a FROM Author a WHERE a.birthDate BETWEEN :start AND :end";
        var query = session.createQuery(jpql, Author.class);
        query.setParameter("start", LocalDate.of(1989, 1, 1));
        query.setParameter("end", LocalDate.of(1996, 1, 1));

        query.list().forEach(System.out::println);

    }

    @Test
    void findByIdIn() {

        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "SELECT a FROM Author a WHERE a.id IN :ids";
        var query = session.createQuery(jpql, Author.class);
        query.setParameter("ids", List.of(1L, 3L));

        query.list().forEach(System.out::println);

    }

    @Test
    void findByAddressCity() {

        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "SELECT a FROM Author a WHERE a.address.city = :city";
        var query = session.createQuery(jpql, Author.class);
        query.setParameter("city", "Madrid");

        query.list().forEach(System.out::println);

    }


    @Test
    void count() {

        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "SELECT count(a.id) FROM Author a";
        var query = session.createQuery(jpql, Long.class);

        Long count = query.getSingleResult();
        System.out.println("num autores: " + count);
    }

    @Test
    void update() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "update Author a set a.name = :name where a.id = :id";
        session.beginTransaction();

        int updated = session.createMutationQuery(jpql)
                .setParameter("name", "Modificado")
                .setParameter("id", 1)
                .executeUpdate();
        session.getTransaction().commit();

        System.out.println("Authors updated: " + updated);
    }


    void insertData() {

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var address1 = new Address("street1", "Madrid", "country1");
        var address2 = new Address("street1", "Barcelona", "country1");
        var address3 = new Address("street1", "Madrid", "country1");

        session.persist(address1);
        session.persist(address2);
        session.persist(address3);


        var author1 = new Author("a1", "a1@email.com", LocalDate.of(1990, 1, 1));
        var author2 = new Author("a2", "a2@email.com", LocalDate.of(1995, 1, 1));
        var author3 = new Author("a3", "a3@email.com", LocalDate.of(2000, 1, 1));

        author1.setAddress(address1);
        author2.setAddress(address2);
        author3.setAddress(address3);

        session.persist(author1);
        session.persist(author2);
        session.persist(author3);

        session.getTransaction().commit();


    }
}
