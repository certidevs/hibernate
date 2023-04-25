package com.example;

import com.example.model.Address;
import com.example.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class HashcodeTest {

    @Test
    void naturalId() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

//        session.find(Employee.class, 1L);

        var emp1a = session.bySimpleNaturalId(Employee.class).load("1111A");
        session.detach(emp1a);

        var emp1b = session.bySimpleNaturalId(Employee.class).load("1111A");
        System.out.println(emp1a.equals(emp1b)); // true
    }

    @Test
    void hashCodeTest() {

        Set<Employee> employees = new HashSet<>();
        for (int i = 0; i < 20_000; i++)
            employees.add(new Employee("emp" + i, String.valueOf(i)));

        System.out.println("====");

        var emp = new Employee("emp5000", "5000");
        // sin override de hashCode no lo encuentra
        // si utiliza el mismo hashcode para todos entonces tarda mucho
        // si utiliza un hashcode diferente para cada objeto (basado en dni) lo encuentra más rápido
        if(employees.contains(emp))
            System.out.println("Employee 5000 found!");


    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var emp1 = new Employee("emp1", "1111A");
        var emp2 = new Employee("emp2", "2222B");

        session.persist(emp1);
        session.persist(emp2);

        session.getTransaction().commit();

    }
}
