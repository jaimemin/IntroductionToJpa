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

            /**
             * Item.class로 찾을 경우 UNION ALL로 다 찾아서 성능이 느림
             *    select
             *         item0_.id as id1_2_0_,
             *         item0_.name as name2_2_0_,
             *         item0_.price as price3_2_0_,
             *         item0_.actor as actor1_6_0_,
             *         item0_.director as director2_6_0_,
             *         item0_.artist as artist1_0_0_,
             *         item0_.author as author1_1_0_,
             *         item0_.isbn as isbn2_1_0_,
             *         item0_.clazz_ as clazz_0_
             *     from
             *         ( select
             *             id,
             *             name,
             *             price,
             *             actor,
             *             director,
             *             null as artist,
             *             null as author,
             *             null as isbn,
             *             1 as clazz_
             *         from
             *             Movie
             *         union
             *         all select
             *             id,
             *             name,
             *             price,
             *             null as actor,
             *             null as director,
             *             artist,
             *             null as author,
             *             null as isbn,
             *             2 as clazz_
             *         from
             *             Album
             *         union
             *         all select
             *             id,
             *             name,
             *             price,
             *             null as actor,
             *             null as director,
             *             null as artist,
             *             author,
             *             isbn,
             *             3 as clazz_
             *         from
             *             Book
             *     ) item0_
             *     where
             *        item0_.id=?
             */
            Item item = entityManager.find(Item.class, movie.getId());
            System.out.println("item = " + item);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

}
