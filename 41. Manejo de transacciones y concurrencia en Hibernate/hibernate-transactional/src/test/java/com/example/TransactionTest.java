package com.example;

import jakarta.transaction.Transactional;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
API Transaction de Hibernate proporciona una manera uniforme de manejar
transacciones independientemente del mecanismo utilizado: JDBC, JTA.
 */

public class TransactionTest {


    // @Transactional
    @Test
    @DisplayName("Modo JDBC: la transacción la gestiona la aplicación localmente")
    public void jdbc_transaction() {
        var session = HibernateUtil.getSessionFactory().openSession();

        try {
            // Modo JDBC: llama a java.sql.Connection#setAutoCommit(false) para iniciar una transacción
            session.getTransaction().begin();
            session.createMutationQuery("UPDATE customer set age = :age where id = :id")
                    .setParameter("age", 20)
                    .setParameter("id", 1L)
                    .executeUpdate();

            // Modo JDBC: llama a java.sql.Connection#commit() para confirmar la transacción
            session.getTransaction().commit();
        } catch (Exception e) {

            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE ||
                    session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK)
                session.getTransaction().rollback();

            e.printStackTrace();
        } finally {
            session.close();
            HibernateUtil.shutdown();
        }
    }

    @Test
    @DisplayName("Modo JTA CMT: la transacción la gestiona el servidor de aplicaciones")
    public void jdbc_jta_cmt() {
        var session = HibernateUtil.getSessionFactory().openSession();

        try {
            // Modo JTA CMT: la transacción la inicia el servidor de aplicaciones
            // session.getTransaction().begin();
            session.createMutationQuery("UPDATE customer set age = :age where id = :id")
                    .setParameter("age", 20)
                    .setParameter("id", 1L)
                    .executeUpdate();

            // Modo JTA CMT: la transacción la confirma el servidor de aplicaciones
            // session.getTransaction().commit();
        } catch (Exception e) {

//            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE ||
//                    session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK)
//                session.getTransaction().rollback();

            e.printStackTrace();
        } finally {
            session.close();
            HibernateUtil.shutdown();
        }
    }

    @Test
    @DisplayName("Modo JTA BMT: la aplicación inicia la transacción e interactúa con JTA")
    public void jdbc_jta_bmt() {
        var session = HibernateUtil.getSessionFactory().openSession();

        try {
            // Modo JTA BMT: esta llamada invoca begin en el UserTransaction y TransactionManager
            session.getTransaction().begin();
            session.createMutationQuery("UPDATE customer set age = :age where id = :id")
                    .setParameter("age", 20)
                    .setParameter("id", 1L)
                    .executeUpdate();

            // Modo JTA BMT: esta llamada invoca commit en el UserTransaction y TransactionManager
             session.getTransaction().commit();
        } catch (Exception e) {

            // Modo JTA BMT: esta llamada invoca rollback en el UserTransaction y TransactionManager

            if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE ||
                    session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK)
                session.getTransaction().rollback();

            e.printStackTrace();
        } finally {
            session.close();
            HibernateUtil.shutdown();
        }
    }
}
