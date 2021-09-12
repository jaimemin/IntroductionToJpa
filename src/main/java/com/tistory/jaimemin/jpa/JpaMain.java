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

            Member member2 = new Member();
            member.setUsername("hello2");
            entityManager.persist(member2);

            entityManager.flush();
            entityManager.clear();

//            Member foundMember = entityManager.find(Member.class, member.getId());
//            Member foundMember2 = entityManager.find(Member.class, member2.getId());
//
//            // true
//            System.out.println("member == member2" + (member.getClass() == member2.getClass()));

            Member foundMember = entityManager.find(Member.class, member.getId());
            Member foundMember2 = entityManager.getReference(Member.class, member2.getId());

            // false (프록시가 넘어오는 경우 있으므로 == 사용하지 말기)
            System.out.println("member == member2" + (member.getClass() == member2.getClass()));

            // true (상속관계이므로)
            System.out.println("member == member2: " + (member instanceof Member));
            System.out.println("member == member2: " + (member2 instanceof Member));

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

}
