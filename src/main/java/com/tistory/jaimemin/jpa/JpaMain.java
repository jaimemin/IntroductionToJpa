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
            Team team = new Team();
            team.setName("teamA");
            entityManager.persist(team);

            Team team2 = new Team();
            team.setName("teamB");
            entityManager.persist(team2);

            Member member = new Member();
            member.setUsername("hello");
            member.setTeam(team);
            entityManager.persist(member);

            // team 따로따로 가져옴 (영속성 컨텍스트 내 해당 team이 없기 때문에)
            // 쿼리가 너무 많이 나가는 문제 (성능 이슈) -> N+1 문제
            Member member2 = new Member();
            member.setUsername("hello2");
            member.setTeam(team2);
            entityManager.persist(member2);

            entityManager.flush();
            entityManager.clear();

            // EAGER이므로 한번에 다 가져옴
            /**
             * select
             *  member0_.MEMBER_ID as member_i1_3_0_,
             *  member0_.createdAt as createda2_3_0_,
             *  member0_.createdBy as createdb3_3_0_,
             *  member0_.lastModifiedAt as lastmodi4_3_0_,
             *  member0_.lastModifiedBy as lastmodi5_3_0_,
             *  member0_.team_TEAM_ID as team_tea7_3_0_,
             *  member0_.USERNAME as username6_3_0_,
             *  team1_.TEAM_ID as team_id1_7_1_,
             *  team1_.createdAt as createda2_7_1_,
             *  team1_.createdBy as createdb3_7_1_,
             *  team1_.lastModifiedAt as lastmodi4_7_1_,
             *  team1_.lastModifiedBy as lastmodi5_7_1_,
             *  team1_.name as name6_7_1_
             * from Member member0_
             * left outer join Team team1_
             * on member0_.team_TEAM_ID=team1_.TEAM_ID where member0_.MEMBER_ID=?
             */
            // Member에 엮여있는 클래스가 많을수록 불필요한 JOIN문이 많이 나가서 성능이 안 나옴
            Member m = entityManager.find(Member.class, member.getId());

            // 쿼리가 두번 나감
            // 1. Member 불러오는 쿼리
            // 2. Team 불러오는 쿼리
            // find의 경우 JPA에서 내부적으로 MEMBER와 TEAM을 모두 세팅하는 것을 알고 있지만
            // JPQL의 경우 그대로 SQL로 번역이 되고 당연히 Member만 SELECT
            // 1. SELECT * FROM MEMBER
            // 2. SELECT * FROM TEAM WHERE TEAM_ID = xxx
            List<Member> members = entityManager.createQuery("select m from Member m", Member.class)
                    .getResultList();
            
            // LAZY로 세팅하고 fetch join을 적용할 경우 원할 때만 JOIN 쿼리 나감
//            List<Member> members = entityManager.createQuery("select m from Member m join fetch m.team", Member.class)
//                    .getResultList();

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
