package com.tistory.jaimemin.jpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory
                = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Child child = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child);
            parent.addChild(child2);

            /**
             * insert
             *         into
             *             Parent
             *             (name, PARENT_ID)
             *         values
             *             (?, ?)
             * insert
             *         into
             *             Child
             *             (name, PARENT_ID, id)
             *         values
             *             (?, ?, ?)
             * insert
             *         into
             *             Child
             *             (name, PARENT_ID, id)
             *         values
             *             (?, ?, ?)
             */
            entityManager.persist(parent);
            // CASCADE 없이 적용해야할 때
//            entityManager.persist(child);
//            entityManager.persist(child2);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();

            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

}
