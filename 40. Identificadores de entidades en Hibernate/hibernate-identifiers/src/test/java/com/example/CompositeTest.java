package com.example;

import com.example.model.Company;
import com.example.model.CompanyPK;
import com.example.model.Employee;
import org.junit.jupiter.api.Test;

public class CompositeTest {

    @Test
    void composedPrimaryKey() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var pk = new CompanyPK("B111", "brand2");
        var company1 = session.find(Company.class, pk);
        System.out.println(company1);
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();


        var comp1a = new Company("B111", "brand1", 20);
        var comp1b = new Company("B111", "brand2", 30);

        session.persist(comp1a);
        session.persist(comp1b);

        session.getTransaction().commit();
    }
}
