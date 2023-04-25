package com.example;

import com.example.model.json.Employee;
import com.example.model.json.EmployeeDetails;
import jakarta.persistence.Tuple;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JsonTest {


    @Test
    void jsonTypeTest() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        var emp1 = session.find(Employee.class, 1L);

        System.out.println(emp1.getDetails());

    }

    @Test
    void jsonTypeQuery() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        String sql = """
                        select e.id, e.details->>'hobbies'
                        from employee e
                        where e.details->> 'location' in :locations
                        """;
        session.createNativeQuery(sql, Tuple.class)
                .setParameter("locations", List.of("madrid", "barcelona"))
                .getResultList()
                .forEach(tuple -> System.out.println(tuple.get(0) + " " + tuple.get(1)));


    }


    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();


        var details1 = new EmployeeDetails("bio1", "madrid", "https://", List.of("h1", "h2", "h3"));
        var details2 = new EmployeeDetails("bio2", "barcelona", "https://", List.of("h1", "h2", "h3"));
        var details3 = new EmployeeDetails("bio3", "leon", "https://", List.of("h1", "h2", "h3"));

        var emp1 = new Employee("emp1", details1);
        var emp2 = new Employee("emp2", details2);
        var emp3 = new Employee("emp3", details3);

        session.persist(emp1);
        session.persist(emp2);
        session.persist(emp3);
        session.getTransaction().commit();

    }
}
