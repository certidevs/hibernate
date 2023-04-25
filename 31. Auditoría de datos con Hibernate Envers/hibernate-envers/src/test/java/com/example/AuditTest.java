package com.example;

import com.example.model.Employee;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

/*
Se genera una nueva tabla employee_aud
RevType:
- 0: ADD
- 1: MOD
- 2: DEL


 */
public class AuditTest {

    @Test
    void find() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();
        var emp1 = session.find(Employee.class, 1L);
        System.out.println(emp1);
    }

    @Test
    void updateDelete() throws InterruptedException {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();
        var emp1 = session.find(Employee.class, 1L);
        System.out.println(emp1);

        // Thread.sleep(5000L);

        emp1.setSalary(emp1.getSalary() + 1000.0);
        session.beginTransaction();
        session.persist(emp1);
        session.getTransaction().commit();

        // Thread.sleep(5000L);

        emp1.setBonus(emp1.getBonus() + 500.0);
        session.beginTransaction();
        session.persist(emp1);
        session.getTransaction().commit();

        session.beginTransaction();
        session.remove(emp1);
        session.getTransaction().commit();


    }
    @Test
    void printRevisions() throws InterruptedException {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();
        var emp1 = session.find(Employee.class, 1L);
        System.out.println(emp1);

        emp1.setSalary(emp1.getSalary() + 1000.0);
        session.beginTransaction();
        session.persist(emp1);
        session.getTransaction().commit();

        emp1.setBonus(emp1.getBonus() + 500.0);
        session.beginTransaction();
        session.persist(emp1);
        session.getTransaction().commit();

        AuditReader auditReader = AuditReaderFactory.get(session);
        List<Number> revisions = auditReader.getRevisions(Employee.class, 1L);

        // print all revisions
        for (Number revision : revisions) {
            Employee emp = auditReader.find(Employee.class, 1L, revision);
            System.out.println(revision + " - " + emp);
        }

        // print one revision per employee
        var results = auditReader.createQuery()
                .forEntitiesAtRevision(Employee.class, revisions.get(0))
                .getResultList();

        System.out.println(results);
    }



    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var employee1 = new Employee("e1@email.com", 1000.0, 100.0);
        var employee2 = new Employee("e1@email.com", 1000.0, 100.0);
        var employee3 = new Employee("e1@email.com", 1000.0, 100.0);
        session.persist(employee1);
        session.persist(employee2);
        session.persist(employee3);
        session.getTransaction().commit();

    }
}
