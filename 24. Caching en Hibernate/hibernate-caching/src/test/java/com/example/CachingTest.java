package com.example;

import com.example.model.Employee;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateHints;
import org.hibernate.stat.CacheRegionStatistics;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class CachingTest {

    @Test
    void caching_1level() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var employee = session.find(Employee.class, 1L);
        System.out.println(employee);

        // utiliza la caché de 1 nivel
        employee = session.find(Employee.class, 1L);
        System.out.println(employee);
    }

    @Test
    void caching_2level_WithoutCache() {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var employee = session.find(Employee.class, 1L);
        System.out.println(employee);
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        // Sin configurar nada vemos que no utiliza la caché de 2 nivel
        // Para que utilice la caché de segundo nivel hay que activarla para esta entidad
        employee = session.find(Employee.class, 1L);
        System.out.println(employee);
    }

    /*
    Añadir en la entidad Employee:
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cacheable
     */
    @Test
    void caching_2level_WithAnnotations() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();
        String model = "com.example.model.Employee";

        // extrae de base de datos
        var employee = session.find(Employee.class, 1L);
        System.out.println(employee);
        Arrays.stream(session.getSessionFactory().getStatistics().getSecondLevelCacheRegionNames())
                .forEach(System.out::println);
        printStats(session, model);
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        // utiliza caché de 2 level
        employee = session.find(Employee.class, 1L);
        printStats(session, model);
        System.out.println(employee);
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        // utiliza caché de 2 level
        employee = session.find(Employee.class, 1L);
        printStats(session, model);
        System.out.println(employee);
        session.close();


    }


    @Test
    void caching_2level_WithQueryHint() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();
        String jpql = "select e from Employee e where e.id = :id";
        String model = "com.example.model.Employee";
        Long id = 1L;

        // 1. extrae de base de datos
        var employee = session.createQuery(jpql, Employee.class)
                        .setParameter("id", id)
                        .setCacheable(true)
                        .setCacheRegion(model)
//                .setHint(HibernateHints.HINT_CACHEABLE, true)
//                .setHint(HibernateHints.HINT_CACHE_REGION, model)
                        .getSingleResult();

        System.out.println(employee);
        Arrays.stream(session.getSessionFactory().getStatistics().getSecondLevelCacheRegionNames())
                .forEach(System.out::println);
        printStats(session, model);
        session.close();




        // 2. utiliza caché de 2 level
        session = HibernateUtil.getSessionFactory().openSession();
        employee = session.createQuery(jpql, Employee.class)
                .setParameter("id", id)
                .setCacheable(true)
                .setCacheRegion(model)
                .getSingleResult();
        printStats(session, model);
        System.out.println(employee);
        // session.getSessionFactory().getCache().evictQueryRegion(model);
        session.close();


        // 3. utiliza caché de 2 level
        session = HibernateUtil.getSessionFactory().openSession();
        employee = session.createQuery(jpql, Employee.class)
                .setParameter("id", id)
                .setCacheable(true)
                .setCacheRegion(model)
                .getSingleResult();
        printStats(session, model);
        System.out.println(employee);
        session.close();


    }

    private void printStats(Session session, String model){
        // System.out.println(session.getSessionFactory().getStatistics().getSecondLevelCacheHitCount());

        Statistics statistics = session.getSessionFactory().getStatistics();
        CacheRegionStatistics secondLevelCacheStatistics =
                statistics.getDomainDataRegionStatistics(model);
        long hitCount = secondLevelCacheStatistics.getHitCount();
        long missCount = secondLevelCacheStatistics.getMissCount();
        double hitRatio = (double) hitCount / (hitCount + missCount);
        System.out.println("Hit count: " + hitCount);
        System.out.println("Miss count: " + missCount);
        System.out.println("Hit ratio: " + hitRatio);

    }


    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var emp1 = new Employee("111", "emp1");
        var emp2 = new Employee("222", "emp2");
        var emp3 = new Employee("333", "emp3");

        session.persist(emp1);
        session.persist(emp2);
        session.persist(emp3);

        session.getTransaction().commit();

    }
}
