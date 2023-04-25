package com.example;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import com.example.model.Employee;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

public class BlazeTest {

    @Test
    void criteriaJPATest() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        query.select(root);
        
        session.createQuery(query).list().forEach(System.out::println);
    }

    @Test
    void blazeFindAll() {
        insertData();
        var sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        CriteriaBuilderConfiguration config = Criteria.getDefault();
        CriteriaBuilderFactory cbf = config.createCriteriaBuilderFactory(sessionFactory);

        var employees = cbf.create(session, Employee.class)
                .from(Employee.class).getResultList();

        employees.forEach(System.out::println);
    }

    @Test
    void blazeFindById() {
        insertData();
        var sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        CriteriaBuilderConfiguration config = Criteria.getDefault();
        CriteriaBuilderFactory cbf = config.createCriteriaBuilderFactory(sessionFactory);

        var employee = cbf
                .create(session, Employee.class)
                .from(Employee.class)
                .where("id").eq(1L)
                .getSingleResult();

        System.out.println(employee);
    }

    @Test
    void idInIdsList() {
        insertData();
        var sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        CriteriaBuilderConfiguration config = Criteria.getDefault();
        CriteriaBuilderFactory cbf = config.createCriteriaBuilderFactory(sessionFactory);

        cbf.create(session, Employee.class, "e")
                .where("e.id").in(1L, 3L)
                .getResultList()
                .forEach(System.out::println);

    }

    @Test
    void multipleWhere() {
        insertData();
        var sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        CriteriaBuilderConfiguration config = Criteria.getDefault();
        CriteriaBuilderFactory cbf = config.createCriteriaBuilderFactory(sessionFactory);

        cbf.create(session, Employee.class, "e")
                .whereOr()
                    .where("e.age").betweenExpression("18").andExpression("25")
                    .where("e.age").betweenExpression("40").andExpression("50")
                    .whereAnd()
                        .where("e.salary").gt(4500.0)
                        .where("e.salary").lt(5500.0)
                    .endAnd()
                .endOr()
                .getResultList()
                .forEach(System.out::println);

    }

    @Test
    void like() {
        insertData();
        var sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        CriteriaBuilderConfiguration config = Criteria.getDefault();
        CriteriaBuilderFactory cbf = config.createCriteriaBuilderFactory(sessionFactory);

        cbf.create(session, Employee.class, "e")
                .where("e.name").like().value("emp%").noEscape()
                .getResultList()
                .forEach(System.out::println);

    }

    @Test
    void orderBy() {

        insertData();
        var sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        CriteriaBuilderConfiguration config = Criteria.getDefault();
        CriteriaBuilderFactory cbf = config.createCriteriaBuilderFactory(sessionFactory);

        cbf.create(session, Employee.class, "e")
                .orderByDesc("e.salary")
                .getResultList()
                .forEach(System.out::println);

    }

    @Test
    void pagination() {

        insertData();
        var sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        CriteriaBuilderConfiguration config = Criteria.getDefault();
        CriteriaBuilderFactory cbf = config.createCriteriaBuilderFactory(sessionFactory);

        cbf.create(session, Employee.class, "e")
                .orderByAsc("e.id")
                .page(0, 2)
                .getResultList()
                .forEach(System.out::println);

        System.out.println("=====");

        cbf.create(session, Employee.class, "e")
                .orderByAsc("e.id")
                .page(2, 2)
                .getResultList()
                .forEach(System.out::println);

        System.out.println("=====");

        cbf.create(session, Employee.class, "e")
                .orderByAsc("e.id")
                .setFirstResult(0)
                .setMaxResults(2)
                .getResultList()
                .forEach(System.out::println);

    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var emp1 = new Employee("emp1", 26, 1000.0);
        var emp2 = new Employee("emp2", 19, 5000.0);
        var emp3 = new Employee("emm3", 20, 6000.0);

        session.persist(emp1);
        session.persist(emp2);
        session.persist(emp3);

        session.getTransaction().commit();
    }
}
