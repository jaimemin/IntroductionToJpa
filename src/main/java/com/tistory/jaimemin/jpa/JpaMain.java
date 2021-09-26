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

            // 간단할 때만 값 타입 컬렉션
            // 그 외에는 엔티티로 (주소 이력 같은 경우)
            sampleMember.getFavoriteFoods().add("치킨");
            sampleMember.getFavoriteFoods().add("피자");
            sampleMember.getFavoriteFoods().add("족발");

            sampleMember.getAddressHistory().add(new AddressEntity("old1"
                    , "street"
                    , "100000"));
            sampleMember.getAddressHistory().add(new AddressEntity("old2"
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
            List<AddressEntity> addressHistory = foundSampleMember.getAddressHistory();

            for (AddressEntity addressEntity : addressHistory) {
                System.out.println("address = " + addressEntity.getAddress().getCity());
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

            // 앞선 문제의 대안
            // Row update: update ADDRESS set  where id=? 
            // 일대다에서 update일 수 밖에 없음
            // 자체적인 엔티티가 생겨 마음껏 수정할 수 있음
            AddressEntity foundAddressEntity = foundSampleMember.getAddressHistory().get(0);
            foundSampleMember.getAddressHistory().remove(foundAddressEntity);

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
