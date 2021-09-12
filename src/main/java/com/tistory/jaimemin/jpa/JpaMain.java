package com.tistory.jaimemin.jpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory
                = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Member member = new Member();
            member.setUsername("hello");
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            // 프록시
            Member reference = entityManager.getReference(Member.class, member.getId());
            System.out.println("foundMember.getClass() = " + reference.getClass());

            // false
            System.out.println("isLoaded = " + entityManagerFactory
                    .getPersistenceUnitUtil()
                    .isLoaded(reference));

            // reference.getUsername(); // 프록시 초기화
            Hibernate.initialize(reference); // 강제 초기화 (JPA 표준에는 강제 초기화가 없음)
            
            // true
            System.out.println("isLoaded = " + entityManagerFactory
                    .getPersistenceUnitUtil()
                    .isLoaded(reference));

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
