package com.example.monolithic.entities;

import lombok.Getter;
import lombok.Setter;
//import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Getter
    @Setter
    private String postInfo;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    public Post(){
    }

    public Post(Person person, String postInfo) {
        this.person = person;
        this.postInfo = postInfo;
    }
}
