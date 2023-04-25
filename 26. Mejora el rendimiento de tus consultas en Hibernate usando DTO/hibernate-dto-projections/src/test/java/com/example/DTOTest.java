package com.example;

import com.example.dto.EmployeeCountry;
import com.example.dto.EmployeeEmail;
import com.example.model.Address;
import com.example.model.Employee;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.junit.jupiter.api.Test;

public class DTOTest {


    @Test
    void dtoClassInJPQL() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select new com.example.dto.EmployeeEmail(e.id, e.email)
                from Employee e
                """;

        session.createQuery(jpql, EmployeeEmail.class)
                .list()
                .forEach(System.out::println);
    }

    @Test
    void dtoClassWithAssociationInJPQL() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select new com.example.dto.EmployeeCountry(e.id, e.address.country)
                from Employee e
                """;

        session.createQuery(jpql, EmployeeCountry.class)
                .list()
                .forEach(System.out::println);
    }

    @Test
    void tuplesJPQL() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = """
                select e.id as id, e.email as email
                from Employee e
                """;

        session.createQuery(jpql, Tuple.class)
                .list()
                .forEach(tuple -> System.out.println(tuple.get("id") + " " + tuple.get("email")));
    }

    @Test
    void tuplesSQL() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String sql = """
                select e.id as id, e.email as email, a.country as country
                from employee e JOIN address a on a.id = e.address_id
                """;

        session.createNativeQuery(sql, Tuple.class)
                .list()
                .forEach(tuple -> System.out.println(tuple.get("id") + " " + tuple.get("email") + " " + tuple.get("country")));
    }

    @Test
    void criteria() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<EmployeeCountry> cq = cb.createQuery(EmployeeCountry.class);

        Root<Employee> root = cq.from(Employee.class);
        Join<Employee, Address> address = root.join("address");

        cq.select(cb.construct(EmployeeCountry.class, root.get("id"), address.get("country")));

        session.createQuery(cq)
                .list()
                .forEach(System.out::println);
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var address1 = new Address("street1", "11111", "city", "country1");
        var address2 = new Address("street1", "11111", "city", "country1");
        var address3 = new Address("street1", "11111", "city", "country1");

        var emp1 = new Employee("emp1", "emp1", "e1@email.com", address1);
        var emp2 = new Employee("emp2", "emp2", "e2@email.com", address2);
        var emp3 = new Employee("emp3", "emp3", "e3@email.com", address3);

        session.persist(address1);
        session.persist(address2);
        session.persist(address3);

        session.persist(emp1);
        session.persist(emp2);
        session.persist(emp3);

        session.getTransaction().commit();

    }
}
