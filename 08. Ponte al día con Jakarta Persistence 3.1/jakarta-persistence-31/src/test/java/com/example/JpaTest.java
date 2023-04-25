package com.example;

import com.example.model.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;

public class JpaTest {

    @Test
    void jpql() {
        insertData();
        var em = JpaUtil.getEntityManager();

        em.createQuery("select a from Author a", Author.class)
                .getResultList()
                .forEach(System.out::println);
    }

    @Test
    void criteria() {
        insertData();
        var em = JpaUtil.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Author> query = cb.createQuery(Author.class);
        Root<Author> root = query.from(Author.class);
        query.select(root);
        em.createQuery(query).getResultList().forEach(System.out::println);

    }

    void insertData(){

        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();

        var a1 = new Author("a1");
        var a2 = new Author("a2");

        em.persist(a1);
        em.persist(a2);

        em.getTransaction().commit();
        em.close();
    }
}
