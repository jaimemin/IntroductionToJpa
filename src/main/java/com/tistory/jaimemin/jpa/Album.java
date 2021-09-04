package com.tistory.jaimemin.jpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ALBUM")
public class Album extends Item {

    private String artist;
}
