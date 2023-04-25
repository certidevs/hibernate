package com.example;

import com.example.model.Car;
import com.example.model.Employee;
import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import org.hibernate.Hibernate;
import org.hibernate.jpa.HibernateHints;
import org.junit.jupiter.api.Test;


public class QueryHintTest {

    @Test
    void entityGraph() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var graph = session.createEntityGraph(Employee.class);
        graph.addAttributeNodes("cars");

        session.createQuery("select e FROM Employee e", Employee.class)
                .setHint("jakarta.persistence.fetchgraph", graph)
                .list()
                .forEach(e -> System.out.println(e.getCars().size()));
    }

    @Test
    void readOnly_Jakarta() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        // Método sethint viene de Jakarta
        session.createQuery("select e FROM Employee e", Employee.class)
                .setHint(HibernateHints.HINT_READ_ONLY, true)
                .list()
                .forEach(System.out::println);
    }

    @Test
    void readOnly_Hibernate() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        session.createQuery("select e FROM Employee e", Employee.class)
                .setReadOnly(true)
                .list()
                .forEach(System.out::println);
    }

    @Test
    void readOnly_NamedAnnotation() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        session.createNamedQuery("find_all_employees", Employee.class)
                .list()
                .forEach(System.out::println);
    }


    @Test
    void timeout() {
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        for(int i = 0; i < 150_000; i++){
            session.persist(new Employee("Employee " + i));
        }
        session.getTransaction().commit();
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        session.createQuery("select e FROM Employee e", Employee.class)
//                .setHint("jakarta.persistence.query.timeout", 5)
//                .setHint(HibernateHints.HINT_TIMEOUT, 5)
                .setTimeout(1)
                .list()
                .forEach(System.out::println);

        session.close();
    }


    @Test
    void comment() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        Long id = 1L;
        session = HibernateUtil.getSessionFactory().openSession();
        session.createQuery("select e FROM Employee e where e.id = :id", Employee.class)
                .setParameter("id", id)
                .setHint(HibernateHints.HINT_COMMENT, "select where id = " + id)
//                .setComment("This is a comment")
                .list()
                .forEach(System.out::println);

        session.close();
    }

    @Test
    void cache() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        Long id = 1L;
        session = HibernateUtil.getSessionFactory().openSession();
        session.createQuery("select e FROM Employee e where e.id = :id", Employee.class)
                .setParameter("id", id)
                .setHint(HibernateHints.HINT_CACHEABLE, true)
                .setHint(HibernateHints.HINT_CACHE_REGION, "employee")
                .setHint("jakarta.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
                .setHint("jakarta.persistence.cache.storeMode", CacheStoreMode.REFRESH)
                .list()
                .forEach(System.out::println);

        session.close();
    }

    @Test
    void fetchSize() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        session.createQuery("select e FROM Car e", Car.class)
                .setHint(HibernateHints.HINT_FETCH_SIZE, 2)
//                .setFetchSize(2)
                .list()
                .forEach(System.out::println);
    }


    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var emp1 = new Employee("emp1");
        session.persist(emp1);

        var c1 = new Car("c1", emp1);
        var c2 = new Car("c2", emp1);
        var c3 = new Car("c3", emp1);

        session.persist(c1);
        session.persist(c2);
        session.persist(c3);
        session.getTransaction().commit();

    }
}
