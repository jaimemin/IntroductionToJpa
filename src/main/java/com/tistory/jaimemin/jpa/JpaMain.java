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
                
            Member foundMember = entityManager.find(Member.class, member.getId());
            System.out.println("foundMember.getClass() = " + foundMember.getClass());
            
            // 현재 영속성 컨텍스트에 foundMember가 올라와있는 상태
            
            Member reference = entityManager.getReference(Member.class, member.getId());
            System.out.println("reference.getClass() = " + reference.getClass());
            
            // JPA에서는 같은 트랜잭션 내 즉, 같은 영속성 컨텍스트 내에서는 ==는 동일
            System.out.println("a == a: " + (member == reference));

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

}
