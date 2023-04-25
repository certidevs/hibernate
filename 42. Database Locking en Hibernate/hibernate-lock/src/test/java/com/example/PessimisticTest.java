package com.example;

import com.example.model.pessimistic.Employee;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PessimisticTest {

    @Test
    void pessimisticLock() throws InterruptedException {
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        var emp1 = new Employee("e1@email", 2000.0);
        session.persist(emp1);
        session.getTransaction().commit();

        ExecutorService es = Executors.newFixedThreadPool(2);
        es.execute(this::persistAndReadEmployee);
        es.execute(this::persistAndReadEmployee);
        es.shutdown();
        es.awaitTermination(1, TimeUnit.MINUTES);
    }
    void persistAndReadEmployee(){
        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var employee = session.find(Employee.class, 1L);


        session.buildLockRequest(LockOptions.NONE)
                .setLockMode(LockMode.PESSIMISTIC_WRITE)
                .setTimeOut(LockOptions.NO_WAIT)
//                .setTimeOut(4000)
                .lock(employee);

        try {
            employee.setEmail(Thread.currentThread().getName());
            session.getTransaction().commit();
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(employee);
        session.close();


    }

}
