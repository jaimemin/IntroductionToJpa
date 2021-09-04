package com.tistory.jaimemin.jpa;

import javax.persistence.*;

/**
 * 구현 클래스마다 테이블 전략
 * ITEM 테이블은 생성되지 않음
 * ALBUM, BOOK, MOVIE 테이블만 생성됨
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn // 단일 테이블 전략의 경우 해당 어노테이션 없어도 생성됨
public abstract class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
