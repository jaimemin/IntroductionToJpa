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
            SampleMember sampleMember = new SampleMember();
            sampleMember.setUsername("hello");
            sampleMember.setHomeAddress(new Address("city"
                    , "street"
                    , "100000"));
            sampleMember.setWorkPeriod(new Period(LocalDateTime.now()
                    , LocalDateTime.now()));
            entityManager.persist(sampleMember);

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
