package com.example;

import com.example.model.HouseRental;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.util.Arrays;

public class PolygonTest {

    @Test
    void name() throws ParseException {
        insertData();

        var session = HibernateUtil.getSessionFactory().openSession();

        var houseRental = session.find(HouseRental.class, 1L);

        System.out.println(Arrays.toString(houseRental.getSurface().getCoordinates()));
    }

    void insertData() throws ParseException {

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        WKTReader reader = new WKTReader();
        Geometry surface = reader.read("POLYGON ((-3.8252679 40.3785329, -4.0010491 40.336674, -3.7524835 40.3052628, -3.8019219 40.1836686, -3.6288873 40.2623105, -3.3707086 40.2392517, -3.5217706 40.3251583, -3.4036676 40.445453, -3.5657159 40.4193203, -3.712658 40.5363159, -3.8252679 40.3785329))");
        var house = new HouseRental("luxury house", 6, (Polygon) surface);

        session.persist(house);

        session.getTransaction().commit();
    }
}
