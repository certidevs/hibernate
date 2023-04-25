package com.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = // equivalencia SessionFactory
            Persistence.createEntityManagerFactory("hibernate-clase-jpa");

    public static EntityManager getEntityManager(){ // equivalencia Session
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }
}
