package com.example;

import com.example.model.Address;
import com.example.model.Author;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class FetchingOneTest {

    @Test
    void findAuthorById() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        // Si es EAGER se recupera el address junto al autor ya desde el principio
        var a1 = session.find(Author.class, 1L);
        System.out.println("=============");

        // session.close(); // LazyInitializationException: could not initialize proxy [

        // Si es LAZY se lanza el select justo cuando se solicita el address
        System.out.println(a1.getAddress().getCountry());
    }

    @Test
    void findAuthorByIdWithAddress() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select a from Author a
                join fetch a.address
                where a.id = :id
                """;

        var a1 = session.createQuery(jpql, Author.class)
                .setParameter("id", 1L)
                .getSingleResult();

        System.out.println("===============");
        System.out.println(a1.getAddress().getCountry());

    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        var address1 = new Address("street1", "city1", "country1");
        var address2 = new Address("street2", "city1", "country1");
        var address3 = new Address("street3", "city1", "country1");

        var author1 = new Author("author1", "a1@email.com", LocalDate.of(1990, 1, 1));
        var author2 = new Author("author2", "a2@email.com", LocalDate.of(1990, 1, 1));
        var author3 = new Author("author3", "a3@email.com", LocalDate.of(1990, 1, 1));

        session.beginTransaction();
        session.persist(address1);
        session.persist(address2);
        session.persist(address3);

        author1.setAddress(address1);
        session.persist(author1);
        session.persist(author2);
        session.persist(author3);

        session.getTransaction().commit();

    }
}
