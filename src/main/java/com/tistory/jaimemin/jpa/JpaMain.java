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
            Team team = new Team();
            team.setName("TeamA");

            entityManager.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);

            entityManager.persist(member);
            entityManager.flush();
            entityManager.clear();

            Member foundMember = entityManager.find(Member.class, member.getId());
            Team foundTeam = foundMember.getTeam();
            List<Member> members = foundTeam.getMembers();

            for (Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

}
