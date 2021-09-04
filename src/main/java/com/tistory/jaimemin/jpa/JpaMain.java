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
            Movie movie = new Movie();
            movie.setDirector("directorA");
            movie.setActor("actorA");
            movie.setName("movieA");
            movie.setPrice(10000);
            entityManager.persist(movie);

            // 영속성 컨텍스트 flush
            entityManager.flush();
            entityManager.clear();

            // Join을 통해 조회
            /**
             *    select
             *         movie0_.id as id1_2_0_,
             *         movie0_1_.name as name2_2_0_,
             *         movie0_1_.price as price3_2_0_,
             *         movie0_.actor as actor1_6_0_,
             *         movie0_.director as director2_6_0_
             *     from
             *         Movie movie0_
             *     inner join
             *         Item movie0_1_
             *             on movie0_.id=movie0_1_.id
             *     where
             *         movie0_.id=?
             */
            Movie foundMovie = entityManager.find(Movie.class, movie.getId());
            System.out.println("foundMovie = " + foundMovie);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

}
