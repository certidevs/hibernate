package com.example;

import com.example.model.Address;
import com.example.model.Author;
import com.example.model.QAddress;
import com.example.model.QAuthor;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class QueryDSLTest {


    @Test
    void findByName() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        QAddress adr = QAddress.address;
        JPAQuery<Address> query = new JPAQuery<>(session);

        query.select(adr)
                .from(adr)
                .where(adr.city.eq("Madrid"))
                .fetch()
                .forEach(System.out::println);
    }


    @Test
    void expression() {

        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var author = QAuthor.author;

        BooleanExpression filter =
                // atributos
                author.name.startsWith("a")
                .and(author.salary.goe(900))
                .and(author.dept.eq("dept1").or(author.dept.eq("dept3")))
                .and(author.category.in("cat1", "cat4"))
                .and(author.birth.between(LocalDate.of(1990, 1, 1), LocalDate.of(2000, 1, 1)))
                .and(author.married.isFalse())
                // asociaciones
                .and(author.address.city.eq("Madrid"))
                .and(author.books.any().title.startsWith("Comedia").not());

        var orders = new OrderSpecifier[]{author.salary.desc(), author.name.asc()};

        new JPAQuery<>(session)
                .select(author)
                .from(author)
                .where(filter)
                .orderBy(orders)
                .fetch()
                .forEach(System.out::println);
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var adr1 = new Address("Madrid", "calle falsa");
        var adr2 = new Address("Barcelona", "calle falsa");
        var adr3 = new Address("Madrid", "calle falsa");

        session.persist(adr1);
        session.persist(adr2);
        session.persist(adr3);

        var a1 = new Author("a1", "dept1", "cat1", 1000, LocalDate.of(1990, 1, 1), true, adr1);
        var a2 = new Author("a2", "dept2", "cat2", 1000, LocalDate.of(1990, 1, 1), true, adr2);
        var a3 = new Author("a3", "dept3", "cat3", 1000, LocalDate.of(1990, 1, 1), true, adr3);
        session.persist(a1);
        session.persist(a2);
        session.persist(a3);

        session.getTransaction().commit();

    }
}
