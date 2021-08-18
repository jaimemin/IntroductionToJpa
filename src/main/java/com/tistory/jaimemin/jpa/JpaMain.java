package com.tistory.jaimemin.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory
                = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // insert
            // entityManager.persist(getDefaultMember());

            // find
            // Member member = entityManager.find(Member.class, 1L);
            List<Member> members = entityManager.createQuery("select m from Member as m", Member.class)
                    .getResultList();

            for (Member member : members) {
                System.out.println("member.getName() = " + member.getName());
            }

            // delete
            // entityManager.remove(member);

            // update
            // member.setName("HelloJPA");

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

    public static Member getDefaultMember() {
        Member member = new Member();
        member.setId(2L);
        member.setName("HelloB");

        return member;
    }
}
