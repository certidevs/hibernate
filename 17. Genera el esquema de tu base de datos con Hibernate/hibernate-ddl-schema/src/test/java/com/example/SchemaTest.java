package com.example;

import com.example.model.Address;
import org.junit.jupiter.api.Test;

public class SchemaTest {

    @Test
    void name() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var addr1 = session.find(Address.class, 1L);
        System.out.println(addr1);
        session.close();
        HibernateUtil.shutdown();
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var address1 = new Address("street1", "city1", "country1", "state1");

        session.persist(address1);

        session.getTransaction().commit();

    }
}
