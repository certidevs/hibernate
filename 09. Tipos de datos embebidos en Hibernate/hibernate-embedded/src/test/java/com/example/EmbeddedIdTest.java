package com.example;

import com.example.model.example1.Address;
import com.example.model.example1.Customer;
import com.example.model.example1.Employee;
import com.example.model.example2.Company;
import com.example.model.example2.CompanyPK;
import org.junit.jupiter.api.Test;

public class EmbeddedIdTest {

    @Test
    void embeddedIdTest() {

        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var companyPK1 = new CompanyPK("cif1", "brand1");
        var c1a = session.find(Company.class, companyPK1);
        System.out.println(c1a);

        var companyPK2 = new CompanyPK("cif1", "brand2");
        var c1b = session.find(Company.class, companyPK2);
        System.out.println(c1b);
    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var companyPK1 = new CompanyPK("cif1", "brand1");
        var companyPK2 = new CompanyPK("cif1", "brand2");

        var company1a = new Company(companyPK1, "madrid", 5);
        var company1b = new Company(companyPK2, "barcelona", 10);

        session.persist(company1a);
        session.persist(company1b);

        session.getTransaction().commit();


    }
}
