package com.example;

import com.example.model.optimistic.Customer;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OptimisticTest {


    /*
Si se añade @Version a la clase Customer, se lanza una excepción de tipo OptimisticLockException
que evita que se actualice el thread1 actualice el customer name ya que thread2 lo ha actualizado justo antes

Si no se añade @Version a la clase Customer, no se lanza una excepción de tipo OptimisticLockException
pero el thread1 actualiza el customer name sin tener en cuenta las modificaciones de thread2
 */
    @Test
    void optimisticLockTest() throws InterruptedException {

        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        var customer1 = new Customer("customer1", 20);
        session.persist(customer1);
        session.getTransaction().commit();
        session.close();

        // escenario concurrente
        ExecutorService es = Executors.newFixedThreadPool(2);
        es.execute(this::updateCustomer1);
        es.execute(this::updateCustomer2);
        es.shutdown();
        es.awaitTermination(1, TimeUnit.MINUTES);
    }



    private void updateCustomer1() {
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        System.out.println(Thread.currentThread().getName() + "updating customer");
        var customer = session.find(Customer.class, 1L);
        customer.setName(Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        try {
            session.persist(customer);
            session.getTransaction().commit();
//        } catch (Exception e) {
//            System.out.println(Thread.currentThread().getName() + " " + e.getMessage());
//        }
        System.out.println(Thread.currentThread().getName() + " finished");
        session.close();

    }

    private void updateCustomer2() {
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        System.out.println(Thread.currentThread().getName() + "updating customer");
        var customer = session.find(Customer.class, 1L);
        customer.setName(Thread.currentThread().getName());
//        try {
            session.persist(customer);
            session.getTransaction().commit();
//        } catch (Exception e) {
//            System.out.println(Thread.currentThread().getName() + " " + e.getMessage());
//        }
        System.out.println(Thread.currentThread().getName() + " finished");
        session.close();
    }
}
