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

            entityManager.detach(reference); // 영속성 컨텍스트에서 꺼내버린다면?
            // entityManager.close(); // 만약 영속성 컨텍스트를 꺼버리거나

            /**
             * org.hibernate.LazyInitializationException: could not initialize proxy [com.tistory.jaimemin.jpa.Member#1] - no Session
             * 	at org.hibernate.proxy.AbstractLazyInitializer.initialize(AbstractLazyInitializer.java:170)
             * 	at org.hibernate.proxy.AbstractLazyInitializer.getImplementation(AbstractLazyInitializer.java:310)
             * 	at org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor.intercept(ByteBuddyInterceptor.java:45)
             * 	at org.hibernate.proxy.ProxyConfiguration$InterceptorDispatcher.intercept(ProxyConfiguration.java:95)
             * 	at com.tistory.jaimemin.jpa.Member$HibernateProxy$I2QRjDEY.getUsername(Unknown Source)
             * 	at com.tistory.jaimemin.jpa.JpaMain.main(JpaMain.java:34)
             */
            reference.getUsername(); // 프록시 초기화 시도

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
