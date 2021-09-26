package com.tistory.jaimemin.jpa;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * create table SampleMember (
 *        MEMBER_ID bigint not null,
 *         city varchar(255),
 *         endDate timestamp,
 *         startDate timestamp,
 *         street varchar(255),
 *         USERNAME varchar(255),
 *         zipCode varchar(255),
 *         primary key (MEMBER_ID)
 *     )
 */
@Entity
public class SampleMember {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Embedded
    private Period workPeriod;

    @Embedded
    private Address homeAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
}
