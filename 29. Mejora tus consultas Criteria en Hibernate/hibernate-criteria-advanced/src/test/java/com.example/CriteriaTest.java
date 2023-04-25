package com.example;

import com.example.model.Author;
import com.example.model.Book;
import com.example.model.Employee;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CriteriaTest {

    @Test
    void projectionOneColumn() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<String> query = builder.createQuery(String.class);
        Root<Employee> root = query.from(Employee.class);

        query.select(root.get("email"));
        var emails = session.createQuery(query).list();
        emails.forEach(System.out::println);

    }


    @Test
    void projectionMultiColumnObject() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<Employee> root = query.from(Employee.class);

        query.multiselect(
                root.get("email"),
                root.get("salary"),
                root.get("joinDate")
        );


        var employees = session.createQuery(query).list();
        for (Object[] employee : employees) {
            System.out.println(employee[0]);
            System.out.println(employee[1]);
            System.out.println(employee[2]);
        }

    }

    @Test
    void projectionMultiColumnTuple() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createQuery(Tuple.class);
        Root<Employee> root = query.from(Employee.class);

        query.select(
                builder.tuple(
                        root.get("email"),
                        root.get("salary"),
                        root.get("joinDate")
                )
        );


        List<Tuple> tuples = session.createQuery(query).list();
        tuples.forEach(tuple -> {
            System.out.println(tuple.get(0));
            System.out.println(tuple.get(1));
            System.out.println(tuple.get(2));
        });
    }

    @Test
    void aggregation() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<Employee> root = query.from(Employee.class);

        query.multiselect(
                root.get("city"),
                builder.avg(root.get("salary")),
                builder.avg(root.get("bonus"))
        );
        query.groupBy(root.get("city"))
                .having(builder.gt(builder.avg(root.get("salary")), 4000.0));


        var employees = session.createQuery(query).list();
        employees.forEach(employee -> System.out.println(
                employee[0] + " " + employee[1] + " " + employee[2]
        ));

    }
    @Test
    void orderBy() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<Employee> root = query.from(Employee.class);

        var avgSalary = builder.avg(root.get("salary"));
        query.multiselect(
                root.get("city"),
                avgSalary,
                builder.avg(root.get("bonus"))
        );
        query.groupBy(root.get("city"));
        query.orderBy(builder.desc(avgSalary));


        var employees = session.createQuery(query).list();
        employees.forEach(employee -> System.out.println(
                employee[0] + " " + employee[1] + " " + employee[2]
        ));

    }


    @Test
    void join() {

        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Author> query = builder.createQuery(Author.class);
        Root<Author> root = query.from(Author.class);

        Join<Author, Book> books = root.join("books");

        query.select(root)
                .where(
                        builder.equal(books.get("category"), "novela")
                );

        List<Author> authors = session.createQuery(query).list();
        authors.forEach(System.out::println);

    }

    @Test
    void joinFetch() {

        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Author> query = builder.createQuery(Author.class);
        Root<Author> root = query.from(Author.class);

        Join<Author, Book> books = root.join("books", JoinType.LEFT);
        Fetch<Object, Object> fetch = root.fetch("books", JoinType.LEFT);

        query.select(root)
                .where(
                        builder.equal(books.get("category"), "novela")
                );

        List<Author> authors = session.createQuery(query).list();
        authors.forEach(System.out::println);

    }


    @Test
    void joinFetchEntityGraph() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        EntityGraph<Author> entityGraph = session.createEntityGraph(Author.class);
        entityGraph.addAttributeNodes("books");

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Author> query = builder.createQuery(Author.class);
        Root<Author> root = query.from(Author.class);

        query.select(root);

        List<Author> authors = session.createQuery(query)
//                .setHint("javax.persistence.fetchgraph", entityGraph)
                .setHint("jakarta.persistence.fetchgraph", entityGraph)
                .list();

        authors.forEach(System.out::println);
        authors.forEach(author -> System.out.println(author.getBooks().size()));
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        var phones1 = new ArrayList<>(List.of("11", "22"));
        var emp1 = new Employee("e1@com", 1500.0, 150.0, "c1", "e1 e1", phones1, LocalDateTime.of(2021, 1, 1, 1, 1, 1));

        var phones2 = new ArrayList<>(List.of("11", "22"));
        var emp2 = new Employee("e2@com", 6200.0, 300.0, "c1", "e2 e2", phones2, LocalDateTime.of(2021, 1, 1, 1, 1, 1));

        var phones3 = new ArrayList<>(List.of("11", "22"));
        var emp3 = new Employee("e1@com", 2400.0, 200.0, "c2", "e1 e1", phones3, LocalDateTime.of(2021, 1, 1, 1, 1, 1));

        var phones4 = new ArrayList<>(List.of("11", "22"));
        var emp4 = new Employee("e1@com", 7633.8, 800.0, "c2", "e1 e1", phones4, LocalDateTime.of(2021, 1, 1, 1, 1, 1));

        session.beginTransaction();

        session.persist(emp1);
        session.persist(emp2);
        session.persist(emp3);
        session.persist(emp4);

        var a1 = new Author("a1");
        var a2 = new Author("a2");
        session.persist(a1);
        session.persist(a2);

        var b1 = new Book("b1", a1, "novela");
        var b2 = new Book("b2", a1, "terror");
        var b3 = new Book("b3", a2, "terror");
        var b4 = new Book("b4", a2, "terror");

        session.persist(b1);
        session.persist(b2);
        session.persist(b3);
        session.persist(b4);

        session.getTransaction().commit();
    }
}
