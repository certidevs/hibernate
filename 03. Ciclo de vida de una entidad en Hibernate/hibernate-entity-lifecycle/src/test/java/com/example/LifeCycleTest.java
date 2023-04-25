package com.example;

import com.example.model.Address;
import com.example.model.Author;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

/*

1. Transient
2. Managed
3. Detached
4. Removed
 */
public class LifeCycleTest {

    @Test
    void transientState() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Address address = new Address("street1", "city1", "country1");
        System.out.println(session.contains(address));

        // cuando no es managed puede generar problemas si se asocia
        // TransientObjectException: object references an unsaved transient instance
        var author1 = new Author("author1", "a1@email.com", null);
        author1.setAddress(address);

        session.beginTransaction();
        session.persist(author1);
        session.getTransaction().commit();
    }

    @Test
    void detached() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Address address = new Address("street1", "city1", "country1");
        address.setId(1L);

        session.beginTransaction();
        // PersistenceException` : detached entity passed to persist:
        // session.persist(address);
        session.merge(address);
        session.getTransaction().commit();
    }

    @Test
    void detached2() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Address address = new Address("street1", "city1", "country1");
        address.setId(1L);

        session.beginTransaction();
        // PersistenceException` : detached entity passed to persist:
        // session.persist(address);
        session.merge(address);
        session.getTransaction().commit();

        session.detach(address);


        session.beginTransaction();
        // PersistenceException` : detached entity passed to persist:
        var address1 = session.find(Address.class, 1L);
        address1.setCity("Barcelona");
        session.persist(address1);
//        session.merge(address);
        session.getTransaction().commit();
    }

    @Test
    void managed() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Address address = new Address("street1", "city1", "country1");

        session.beginTransaction();
        session.persist(address);
        session.getTransaction().commit();

        System.out.println(session.contains(address));

    }

    @Test
    void removed() {

        Session session = HibernateUtil.getSessionFactory().openSession();
//        Address address = new Address("street1", "city1", "country1");
        Address address = new Address();
        address.setId(2L);

        session.beginTransaction();
        session.remove(address);
        session.getTransaction().commit();

        System.out.println(session.contains(address));
    }

    @Test
    void differentSessions() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        var address3 = session.find(Address.class, 3L);
        System.out.println(session.contains(address3));

        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println(session.contains(address3));
    }

    @Test
    void load() {

        Session session = HibernateUtil.getSessionFactory().openSession();
//        var address3 = session.getReference(Address.class, 3L); // No carga el objeto hasta que no se accede a sus atributos
        var address3 = session.find(Address.class, 3L);
        System.out.println("=========");
        session.close();
        System.out.println(address3.getCountry());
    }
}
