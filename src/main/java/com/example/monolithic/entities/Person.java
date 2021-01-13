package com.example.monolithic.entities;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
//import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
@JsonFilter("BeanToFilter")
@Entity
public class Person {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Getter
    @Setter
    @NotNull
    private String name;

    @Column
    @Getter
    private String job;

    @JsonIgnore
    @Getter
    @Setter
    @OneToMany(mappedBy = "person")
    private List<Post> posts;

    protected Person() {
    }

    public Person(@NotNull String name, String job) {
        this.name = name;
        this.job = job;
    }
}