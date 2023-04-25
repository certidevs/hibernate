package com.example;

import com.example.mappedsuperclass.User;
import com.example.tableperclass.Car;
import com.example.tableperclass.Motorcycle;
import com.example.tableperclass.Owner;
import com.example.tableperclass.Vehicle;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TablePerClassTest {

    /*
Table per class es el enfoque más flexible, se crea una tabla por cada clase, incluyendo la clase base.
Permite polimorfismo en asociaciones, pero hace que las consultas sean más complejas.
 */
    @Test
    void tablePerClass() {
        insertDemoData();
        Session session = HibernateUtil.getSessionFactory().openSession();

//        var cars = session.createQuery("from Car", Car.class).list();
//        cars.forEach(System.out::println);

//        var motos = session.createQuery("from Motorcycle", Motorcycle.class).list();
//        motos.forEach(System.out::println);

        // IMPORTANTE: trae todos los vehículos, incluidos cars y motorcycles
//        var vehicles = session.createQuery("from Vehicle", Vehicle.class).list();
//        vehicles.forEach(System.out::println);

        session.find(Owner.class, 1L).getVehicles().forEach(System.out::println);
    }

    private void insertDemoData() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var owner1 = new Owner("mike");
        session.persist(owner1);

        var car1 = new Car(null, "Ford", owner1, 4);
        var car2 = new Car(null, "BMW", owner1, 4);
        var motorcycle1 = new Motorcycle(null, "Honda", owner1, true);
        var motorcycle2 = new Motorcycle(null, "Suzuki", owner1, true);
        var v1 = new Vehicle(null, "V1", owner1);
        var v2 = new Vehicle(null, "V2", owner1);

        session.persist(car1);
        session.persist(car2);
        session.persist(motorcycle1);
        session.persist(motorcycle2);
        session.persist(v1);
        session.persist(v2);

        session.getTransaction().commit();
        session.close();
    }
}
