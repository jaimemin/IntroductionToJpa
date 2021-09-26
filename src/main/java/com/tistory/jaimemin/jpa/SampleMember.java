package com.tistory.jaimemin.jpa;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * create table SampleMember (
 *        MEMBER_ID bigint not null,
 *         city varchar(255),
 *         street varchar(255),
 *         zipCode varchar(255),
 *         USERNAME varchar(255),
 *         HOME_CITY varchar(255),
 *         HOME_STREET varchar(255),
 *         HOME_ZIP_CODE varchar(255),
 *         endDate timestamp,
 *         startDate timestamp,
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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "HOME_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "HOME_STREET")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "HOME_ZIP_CODE"))
    })
    private Address workAddress;

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
