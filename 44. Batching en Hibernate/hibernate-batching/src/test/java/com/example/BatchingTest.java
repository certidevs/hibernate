package com.example;

import com.example.model.Employee;
import org.junit.jupiter.api.Test;

public class BatchingTest {


    // 20s mysql sin optimizaciones
    // 10s postgresql sin optimizaciones
    // 4.5s postgresql con optimizaciones
    @Test
    void batchInsertWithoutFlush() {

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        // Puede lanzar OutOfMemoryException
        for (int i = 0; i < 100_000; i++)
            session.persist(new Employee("emp" + i, i));

        session.getTransaction().commit();
        session.close();

    }

    // 22s mysql sin optimizaciones
    @Test
    void batchInsertWithFlush() {

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        // Puede lanzar OutOfMemoryException
        int batchSize = 500;
        for (int i = 0; i < 100_000; i++) {
            if (i > 0 && i % batchSize == 0) {
                session.flush();
                session.clear();
            }
            session.persist(new Employee("emp" + i, i));
        }

        session.getTransaction().commit();
        session.close();

    }

    @Test
    void batchInsertWithFlushWithTryCatch() {

        var session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            // Puede lanzar OutOfMemoryException
            int batchSize = 500;
            for (int i = 0; i < 100_000; i++) {
                if (i > 0 && i % batchSize == 0) {
                    session.flush();
                    session.clear();
                }
                session.persist(new Employee("emp" + i, i));
            }

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (session.getTransaction() != null && session.getTransaction().isActive())
                session.getTransaction().rollback();
            throw e;
        } finally {
            if (session != null)
                session.close();
        }


    }


    @Test
    void update() {

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        for (int i = 0; i < 100_000; i++)
            session.persist(new Employee("emp" + i, i));


        session.getTransaction().commit();
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.createQuery("select e from Employee e", Employee.class)
                .list()
                .forEach(e -> e.setAge(e.getAge() + 1));

        session.getTransaction().commit();
        session.close();

    }
}
