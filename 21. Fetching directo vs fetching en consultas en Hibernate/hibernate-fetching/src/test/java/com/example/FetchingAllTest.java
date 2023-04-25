package com.example;

import com.example.model.Address;
import com.example.model.Author;
import com.example.model.Book;
import com.example.model.Category;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class FetchingAllTest {

    @Test
    void name() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select a from Author a
                join fetch a.address ad
                join fetch a.books b
                join fetch b.categories c
                where a.id = :id
                """;

        var a1 = session.createQuery(jpql, Author.class)
                .setParameter("id", 1L)
                .getSingleResult();

        System.out.println("===============");

        System.out.println(a1.getAddress().getCountry());
        System.out.println(a1.getBooks());
        a1.getBooks().forEach(b -> System.out.println(b.getCategories()));

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
        author2.setAddress(address2);
        author3.setAddress(address3);

        session.persist(author1);
        session.persist(author2);
        session.persist(author3);

        var cat1 = new Category("c1", 12);
        var cat2 = new Category("c2", 16);
        var cat3 = new Category("c3", 17);
        var cat4 = new Category("c4", 21);
        session.persist(cat1);
        session.persist(cat2);
        session.persist(cat3);
        session.persist(cat4);


        var book1 = new Book("b1", 1.0, 100, true, author1);
        book1.setCategories(new HashSet<>(Set.of(cat1, cat2)));

        var book2 = new Book("b2", 1.0, 100, true, author1);
        book2.setCategories(new HashSet<>(Set.of(cat1, cat3)));

        var book3 = new Book("b3", 1.0, 100, true, author2);
        book3.setCategories(new HashSet<>(Set.of(cat2, cat3)));

        session.persist(book1);
        session.persist(book2);
        session.persist(book3);

        session.getTransaction().commit();

    }
}
