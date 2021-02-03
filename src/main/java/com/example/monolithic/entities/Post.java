package com.example.monolithic.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
//import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    @Getter
    @Setter
    private String postInfo;

    @Getter
    @Setter
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;

    public Post(){
    }

    public Post(Person person, String postInfo) {
        this.person = person;
        this.postInfo = postInfo;
    }
}
