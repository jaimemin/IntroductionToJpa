package com.tistory.jaimemin.jpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
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
            // 값 타입 저장 예제
            SampleMember sampleMember = new SampleMember();
            sampleMember.setUsername("member1");
            sampleMember.setHomeAddress(new Address("homeCity"
                    , "street"
                    , "100000"));

            sampleMember.getFavoriteFoods().add("치킨");
            sampleMember.getFavoriteFoods().add("피자");
            sampleMember.getFavoriteFoods().add("족발");

            sampleMember.getAddressHistory().add(new Address("old1"
                    , "street"
                    , "100000"));
            sampleMember.getAddressHistory().add(new Address("old2"
                    , "street"
                    , "100000"));

            // favoriteFoods, addressHistory 모두 값 타입이므로 sampleMember에 종속적
            entityManager.persist(sampleMember);

            entityManager.flush();
            entityManager.clear();

            System.out.println("----------------------- 조회 -------------------------");
            /**
             * select
             *         samplememb0_.MEMBER_ID as member_i1_11_0_,
             *         samplememb0_.city as city2_11_0_,
             *         samplememb0_.street as street3_11_0_,
             *         samplememb0_.zipCode as zipcode4_11_0_,
             *         samplememb0_.USERNAME as username5_11_0_ 
             *     from
             *         SampleMember samplememb0_ 
             *     where
             *         samplememb0_.MEMBER_ID=?
             *         
             *     지연로딩
             */
            SampleMember foundSampleMember
                    = entityManager.find(SampleMember.class, sampleMember.getId());
            List<Address> addressHistory = foundSampleMember.getAddressHistory();

            for (Address address : addressHistory) {
                System.out.println("address = " + address.getCity());
            }
            
            Set<String> favoriteFoods = foundSampleMember.getFavoriteFoods();

            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }

            // homeCity -> newCity
            // 절대 이렇게 하면 안됨
            // foundSampleMember.getHomeAddress().setCity("newCity");
            Address oldAddress = foundSampleMember.getHomeAddress();

            // 갈아껴야함
            foundSampleMember.setHomeAddress(new Address("newCity"
                    , oldAddress.getCity()
                    , oldAddress.getZipCode()));

            // 치킨 -> 한식 (remove -> add)
            // collection 값만 변경하더라도 실제 JPA 쿼리가 실행됨 (영속성 전이처럼 행동)
            foundSampleMember.getFavoriteFoods().remove("치킨");
            foundSampleMember.getFavoriteFoods().add("한식");

            // equals랑 hash가 중요해지는 시점
            // addressHistory를 다 지우고
            // 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장
            // 결론: 이렇게 쓰면 안된다 (실행속도 저하)
            // -> 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본키를 구성해야함
            foundSampleMember.getAddressHistory().remove(new Address("old1"
                    , "street"
                    , "100000"));
            foundSampleMember.getAddressHistory().add(new Address("newCity1"
                    , "street"
                    , "100000"));

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
