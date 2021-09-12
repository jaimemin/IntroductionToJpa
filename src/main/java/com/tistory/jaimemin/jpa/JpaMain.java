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
