package com.example;

import com.example.model.Address;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class PointTest {

    @Test
    void name() throws ParseException {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var address = session.find(Address.class, 1L);

        System.out.println("latitude: " + address.getLocation().getCoordinate().x);
        System.out.println("longitude: " + address.getLocation().getCoordinate().y);

    }

    void insertData() throws ParseException {

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        WKTReader reader = new WKTReader();
        Geometry location = reader.read("POINT(41.40338 2.17403)");
        var address = new Address("calle 1", (Point) location);

        session.persist(address);

        session.getTransaction().commit();
    }
}
