package com.example;

import com.example.model.Employee;
import com.example.model.Product;
import com.example.service.BonusService;
import com.example.service.PricingService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TransientTest {

    private BonusService bonusService = new BonusService();
    private PricingService pricingService = new PricingService();
    @Test
    void employeeBonusNull() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select e from Employee e";
        var employees = session.createQuery(jpql, Employee.class)
                .list();

        employees.forEach(System.out::println);
    }

    @Test
    void employeeBonusCalculated() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select e from Employee e";
        var employees = session.createQuery(jpql, Employee.class)
                .list();

        employees.forEach(bonusService::calculateBonus);

        employees.forEach(System.out::println);

    }


    @Test
    void product() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String jpql = "select p from Product p";

        var prices = session.createQuery(jpql, Product.class)
                .getResultStream()
                .map(pricingService::calculatePriceWithTaxes)
                .map(Product::getPriceWithTaxes)
                .toList();


        System.out.println(prices);

        Double totalPrice = session.createQuery(jpql, Product.class)
                .getResultStream()
                .map(pricingService::calculatePriceWithTaxes)
                .map(Product::getPriceWithTaxes)
                .reduce(0.0, Double::sum);


        System.out.println(totalPrice);


    }

    void insertData() {

        var session = HibernateUtil.getSessionFactory().openSession();

        var emp1 = new Employee("e1@email.com", 1000.0, LocalDate.of(2015, 1, 1));
        emp1.setBonus(100.0); // no persiste en base de datos
        var emp2 = new Employee("e1@email.com", 1000.0, LocalDate.of(2016, 1, 1));
        var emp3 = new Employee("e1@email.com", 1000.0, LocalDate.of(2017, 1, 1));
        var emp4 = new Employee("e1@email.com", 1000.0, LocalDate.of(2018, 1, 1));

        session.beginTransaction();

        session.persist(emp1);
        session.persist(emp2);
        session.persist(emp3);
        session.persist(emp4);

        var prod1 = new Product("prod1", 100.0) ;
        var prod2 = new Product("prod2", 100.0) ;
        var prod3 = new Product("prod3", 100.0) ;
        session.persist(prod1);
        session.persist(prod2);
        session.persist(prod3);

        session.getTransaction().commit();
    }
}
