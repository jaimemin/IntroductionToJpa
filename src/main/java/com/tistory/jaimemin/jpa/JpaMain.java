package com.tistory.jaimemin.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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

            // find 호출하는 시점에서 DB가 쿼리를 날림
            // Member foundMember = entityManager.find(Member.class, member.getId());
            
            // getReference 호출하는 시점에는 DB가 쿼리를 날리지 않음
            Member foundMember = entityManager.getReference(Member.class, member.getId());
            
            // 값이 실제 사용되는 시점에는 쿼리가 날라감
            // 프록시 객체가 실제 엔티티로 바뀌는 것이 아니라 실제 엔티티에 접근이 가능한 것임
            // 따라서, class명 같음
            System.out.println("foundMember before getClass() = " + foundMember.getClass());
            System.out.println("foundMember id = " + foundMember.getId());
            System.out.println("foundMember username = " + foundMember.getUsername());
            System.out.println("foundMember after getClass() = " + foundMember.getClass());

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

}
