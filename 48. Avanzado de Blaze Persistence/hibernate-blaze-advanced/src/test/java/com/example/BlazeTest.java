package com.example;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.InsertCriteriaBuilder;
import com.example.cte.AuthorCity;
import com.example.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BlazeTest {

    @Test
    void filterByAssociation() {
        insertData();
        var sessionFactory = HibernateUtil.getSessionFactory();
        var session = sessionFactory.openSession();
        var cbf = Criteria.getDefault().createCriteriaBuilderFactory(sessionFactory);

        cbf.create(session, Author.class, "a")
                .where("a.address.city").eq("barcelona")
                .getResultList()
                .forEach(System.out::println);

    }

    @Test
    void joinFetch() {
        insertData();
        var sessionFactory = HibernateUtil.getSessionFactory();
        var session = sessionFactory.openSession();
        var cbf = Criteria.getDefault().createCriteriaBuilderFactory(sessionFactory);

        cbf.create(session, Author.class, "a")
                .where("a.books.price").gt(30.0)
                .getResultList()
                .forEach(System.out::println);

        System.out.println("===========");

        cbf.create(session, Author.class, "a")
                .leftJoinFetch("a.books", "b")
                .leftJoinFetch("a.address", "adr")
                .where("b.price").gt(30.0)
                .where("adr.city").eq("madrid")
                .getResultList()
                .forEach(a -> {
                    System.out.println(a);
                    System.out.println(a.getBooks());
                });

    }

    @Test
    void insert() {
        insertData();
        var sessionFactory = HibernateUtil.getSessionFactory();
        var session = sessionFactory.openSession();
        var cbf = Criteria.getDefault().createCriteriaBuilderFactory(sessionFactory);

        session.beginTransaction();
        InsertCriteriaBuilder<CustomerHistory> cb = cbf.insert(session, CustomerHistory.class)
                .from(Customer.class, "c")
                .bind("id").select("c.id")
                .bind("name").select("c.name")
                .bind("age").select("c.age")
                .where("c.active").eq(false);
        int inserted = cb.executeUpdate();
        session.getTransaction().commit();
        System.out.println(inserted);

    }

    @Test
    void update() {
        insertData();
        var sessionFactory = HibernateUtil.getSessionFactory();
        var session = sessionFactory.openSession();
        var cbf = Criteria.getDefault().createCriteriaBuilderFactory(sessionFactory);

        session.beginTransaction();

        int updated =cbf.update(session, Author.class)
                .setExpression("name", "UPPER(name)")
                .executeUpdate();

        session.getTransaction().commit();
        System.out.println(updated);

    }

    @Test
    void delete() {
        insertData();
        var sessionFactory = HibernateUtil.getSessionFactory();
        var session = sessionFactory.openSession();
        var cbf = Criteria.getDefault().createCriteriaBuilderFactory(sessionFactory);

        session.beginTransaction();

        int deleted =cbf.delete(session, Book.class)
                .where("price").gt(30.0)
                .executeUpdate();

        session.getTransaction().commit();
        System.out.println(deleted);

    }

    @Test
    void cte() {
        insertData();
        var sessionFactory = HibernateUtil.getSessionFactory();
        var session = sessionFactory.openSession();
        var cbf = Criteria.getDefault().createCriteriaBuilderFactory(sessionFactory);


        CriteriaBuilder<AuthorCity> cb = cbf.create(session, AuthorCity.class)
                .with(AuthorCity.class)
                .from(Author.class, "a")
                .innerJoin("a.address", "adr")
                .bind("id").select("a.id")
                .bind("firstName").select("a.name")
                .bind("city").select("adr.city")
                .end()
                .where("city").eq("madrid");

        List<AuthorCity> result = cb.getResultList();
        result.forEach(System.out::println);


    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        var address1 = new Address("calle falsa", "barcelona");
        var address2 = new Address("calle verdadera", "madrid");
        session.persist(address1);
        session.persist(address2);

        var author1 = new Author("a1", 1000.0, address1);
        var author2 = new Author("a2", 2000.0, address2);
        session.persist(author1);
        session.persist(author2);

        var b1 = new Book("b1", 450, 4.99, "cat1", author1);
        var b2 = new Book("b2", 450, 15.99, "cat1", author1);
        var b3 = new Book("b3", 450, 25.99, "cat1", author2);
        var b4 = new Book("b4", 450, 35.99, "cat1", author2);

        session.persist(b1);
        session.persist(b2);
        session.persist(b3);
        session.persist(b4);

        var cust1 = new Customer("c1", 32, true);
        var cust2 = new Customer("c2", 32, false);
        var cust3 = new Customer("c3", 32, true);
        var cust4 = new Customer("c4", 32, false);

        session.persist(cust1);
        session.persist(cust2);
        session.persist(cust3);
        session.persist(cust4);

        session.getTransaction().commit();

    }
}
