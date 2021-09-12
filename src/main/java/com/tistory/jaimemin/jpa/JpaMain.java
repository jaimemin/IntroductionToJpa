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

            // 프록시
            Member reference = entityManager.getReference(Member.class, member.getId());
            System.out.println("foundMember.getClass() = " + reference.getClass());

            // 멤버가 나와야하는거 아닌가?
            // 조회는 하지만 결국 foundMember도 proxy가 반환이 됨
            Member foundMember = entityManager.getReference(Member.class, member.getId());
            System.out.println("reference.getClass() = " + foundMember.getClass());
            
            // ==이 성립할까?
            // JPA는 이걸 무조건 참이라고 보장해줘야함 (true)
            // 결론: 프록시로 먼저 조회하면 그 이후 find에 대해서도 프록시를 반환함
            // 핵심은 개발을 진행할 때 반환되는 객체가 프록시든 엔티티든 상관없도록 개발하는 것이 중요 (핵심)
            System.out.println("a == a: " + (foundMember == reference));

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

}
