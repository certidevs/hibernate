package com.example;

import com.example.joined.DeviceOwner;
import com.example.singletable.Account;
import com.example.singletable.AccountOwner;
import com.example.singletable.BasicAccount;
import com.example.singletable.PremiumAccount;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SingleTableTest {

    /*
    Una sola tabla
    Permite polimorfismo y las consultas son las más eficientes
     */
    @Test
    void singleTable() {

        insertDemoData();
        Session session = HibernateUtil.getSessionFactory().openSession();

//        var basics = session.createQuery("from BasicAccount", BasicAccount.class).list();
//        basics.forEach(System.out::println);

//        var premiums = session.createQuery("from PremiumAccount", PremiumAccount.class).list();
//        premiums.forEach(System.out::println);
//
//        var accs = session.createQuery("from Account", Account.class).list();
//        accs.forEach(System.out::println);

        session.find(AccountOwner.class, 1L).getAccounts().forEach(System.out::println);


    }

    private void insertDemoData() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var owner = new AccountOwner("mike");
        session.persist(owner);

        var basic1 = new BasicAccount(null, owner, 50);
        var basic2 = new BasicAccount(null, owner, 50);
        var premium1 = new PremiumAccount(null, owner, 100.0);
        var premium2 = new PremiumAccount(null, owner, 100.0);
        var acc1 = new Account(null, owner);
        var acc2 = new Account(null, owner);

        session.persist(basic1);
        session.persist(basic2);
        session.persist(premium1);
        session.persist(premium2);
        session.persist(acc1);
        session.persist(acc2);

        session.getTransaction().commit();
        session.close();
    }


}
