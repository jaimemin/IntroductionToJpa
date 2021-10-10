package com.tistory.jaimemin.jpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory
                = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // Criteria 사용 준비
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Member> query = criteriaBuilder.createQuery(Member.class);

            Root<Member> member = query.from(Member.class);
            CriteriaQuery<Member> criteriaQuery = query.select(member);

            String username = "kim";

            // 장점: 컴파일 시점에 에러 잡아줌
            // 단점: sql 스럽지 않음
            if (username != null) {
                criteriaQuery = criteriaQuery.where(criteriaBuilder.equal(member.get("username"), "kim"));
            }

            List<Member> members = entityManager.createQuery(criteriaQuery)
                            .getResultList();

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
