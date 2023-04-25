package com.example;

import com.example.where.Author;
import com.example.where.Book;
import com.example.wherejointable.Film;
import com.example.wherejointable.FilmGenre;
import com.example.wherejointable.Genre;
import org.junit.jupiter.api.Test;

public class WhereJoinTableTest {

    @Test
    void name() {
        insertData();
        var session = HibernateUtil.getSessionFactory().openSession();

        session.createQuery("from Film", Film.class)
                .getResultList()
                .forEach(film -> film.getGenres().forEach(System.out::println));


//        String jpql = "select a from Author a join fetch a.books where a.id = 1";
//        session.createQuery(jpql, Author.class).getSingleResult().getBooks().forEach(System.out::println);

    }

    void insertData(){

        var session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        var film1 = new Film("film1");
        var film2 = new Film("film2");
        var film3 = new Film("film3");
        var film4 = new Film("film4");

        var genre1 = new Genre("genre1");
        var genre2 = new Genre("genre2");
        var genre3 = new Genre("genre3");

        session.persist(film1);
        session.persist(film2);
        session.persist(film3);
        session.persist(film4);

        session.persist(genre1);
        session.persist(genre2);
        session.persist(genre3);

        var filmGenre1 = new FilmGenre(film1.getId(), genre1.getId(), 12, "C1");
        var filmGenre2 = new FilmGenre(film2.getId(), genre1.getId(), 12, "C2");
        var filmGenre3 = new FilmGenre(film3.getId(), genre1.getId(), 12, "C1");
        var filmGenre4 = new FilmGenre(film4.getId(), genre2.getId(), 12, "C2");
        session.persist(filmGenre1);
        session.persist(filmGenre2);
        session.persist(filmGenre3);
        session.persist(filmGenre4);

        session.getTransaction().commit();

    }
}
