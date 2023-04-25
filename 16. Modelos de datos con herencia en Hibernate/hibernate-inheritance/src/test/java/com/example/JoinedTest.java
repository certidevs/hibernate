package com.example;

import com.example.joined.Computer;
import com.example.joined.Device;
import com.example.joined.DeviceOwner;
import com.example.joined.Phone;
import com.example.tableperclass.Owner;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JoinedTest {


    /*
    Una tabla para cada entidad, incluida la entidad base
    Permite polimorfismo
    La tabla base tiene las columnas comunes
    Las tablas hijas solo tienen las columnas específicas
    Es por esto que se realiza join para extraer los atributos de todas las tablas involucradas
     */
    @Test
    void joinedTable() {

        insertDemoData();
        Session session = HibernateUtil.getSessionFactory().openSession();

//        var comps = session.createQuery("from Computer", Computer.class).list();
//        comps.forEach(System.out::println);

//        var phones = session.createQuery("from Phone", Phone.class).list();
//        phones.forEach(System.out::println);

        session.find(DeviceOwner.class, 1L).getDevices().forEach(System.out::println);

    }

    private void insertDemoData() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var owner = new DeviceOwner("mike");
        session.persist(owner);

        var comp1 = new Computer(null, "asus", owner, "i7");
        var comp2 = new Computer(null, "msi", owner, "i9");
        var phone1 = new Phone(null, "Onep", owner, "654987987");
        var phone2 = new Phone(null, "Sams", owner, "654987987");

        session.persist(comp1);
        session.persist(comp2);
        session.persist(phone1);
        session.persist(phone2);

        session.getTransaction().commit();
        session.close();
    }

}
