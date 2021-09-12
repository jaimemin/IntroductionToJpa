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
            Team team = new Team();
            team.setName("teamA");
            entityManager.persist(team);

            Member member = new Member();
            member.setUsername("hello");
            member.setTeam(team);
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            Member m = entityManager.find(Member.class, member.getId());
            // Lazy이므로 proxy 형태로 나옴
            System.out.println("m = " + m.getTeam().getClass());

            System.out.println("================");
            m.getTeam().getName(); // 프록시 초기화
            System.out.println("================");

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
