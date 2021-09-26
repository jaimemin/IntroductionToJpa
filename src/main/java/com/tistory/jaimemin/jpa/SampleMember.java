package com.tistory.jaimemin.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String city;

    private String street;

    private String zipCode;
}
