package com.example;

import com.example.model.Address;
import org.junit.jupiter.api.Test;

public class EqualsTest {

    @Test
    void equalsOk() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var address1a = session.find(Address.class, 1L);
        var address1b = session.find(Address.class, 1L);

        System.out.println(address1a.equals(address1b));// true
    }

    @Test
    void beforeOverrideEquals_Detach() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var address1a = session.find(Address.class, 1L);
        session.detach(address1a);

        var address1b = session.find(Address.class, 1L);

        System.out.println(address1a.equals(address1b)); // false

        // mal uso de merge
//        session.merge(address1a);
//        System.out.println(address1a.equals(address1b)); // false

        address1a = session.merge(address1a);
        System.out.println(address1a.equals(address1b)); // true
    }

    @Test
    void beforeOverrideEquals_Close() {
        insertData();
        var session1 = HibernateUtil.getSessionFactory().openSession();

        var address1a = session1.find(Address.class, 1L);
        session1.close();

        var session2 = HibernateUtil.getSessionFactory().openSession();
        var address1b = session2.find(Address.class, 1L);

        System.out.println(address1a.equals(address1b)); // false

        address1a = session2.merge(address1a);
        System.out.println(address1a.equals(address1b)); // true
    }

    @Test
    void afterOverrideEquals_Close() {
        insertData();
        var session1 = HibernateUtil.getSessionFactory().openSession();

        var address1a = session1.find(Address.class, 1L);
        session1.close();

        var session2 = HibernateUtil.getSessionFactory().openSession();
        var address1b = session2.find(Address.class, 1L);

        // Se usa el equals que se acaba de sobreescribir
        System.out.println(address1a.equals(address1b)); // true

//        address1a = session2.merge(address1a);
//        System.out.println(address1a.equals(address1b)); // true
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var add1 = new Address("s1", "c1", "c1");
        var add2 = new Address("s2", "c2", "c2");

        session.persist(add1);
        session.persist(add2);

        session.getTransaction().commit();

    }
}
