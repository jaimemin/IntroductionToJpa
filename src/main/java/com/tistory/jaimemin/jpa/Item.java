package com.tistory.jaimemin.jpa;

import javax.persistence.*;

/**
 * 단일 테이블 전략
 *     create table Item (
 *        DTYPE varchar(31) not null,
 *         id bigint not null,
 *         name varchar(255),
 *         price integer not null,
 *         actor varchar(255),
 *         director varchar(255),
 *         artist varchar(255),
 *         author varchar(255),
 *         isbn varchar(255),
 *         primary key (id)
 *     )
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn // 단일 테이블 전략의 경우 해당 어노테이션 없어도 생성됨
public class Item {

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
