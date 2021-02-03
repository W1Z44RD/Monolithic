package com.example.monolithic;


import com.example.monolithic.entities.Person;
import com.example.monolithic.entities.Post;
import com.example.monolithic.jpainteractions.PersonJPA;
import com.example.monolithic.jpainteractions.PostJPA;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
public class ServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Person person;

    @MockBean
    private PersonJPA personJPA;

    @MockBean
    private PostJPA postJPA;

    @Test
    public void testPerson() throws Exception{

        when(personJPA.findAll()).thenReturn(Arrays.asList(
                new Person("Harrison", "Programmer"),
                new Person("Artem", "Skiving School")));

        mockMvc.perform(get("/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Harrison"))
                .andExpect(jsonPath("$[0].job").value("Programmer"))
                .andExpect(jsonPath("$[1].name").value("Artem"))
                .andExpect(jsonPath("$[1].job").value("Skiving School"));
    }

    @Test
    public void testPostsById() throws Exception {
//        Person person1 = new Person("Harrison", "p");
        Post post = new Post(person, "person");
        Post post1 = new Post(person, "person");

        when(personJPA.findById(1L)).thenReturn(java.util.Optional.ofNullable(person));

        when(person.getPosts()).thenReturn(Arrays.asList(post, post1));

        this.mockMvc.perform(get("/getPost/1"))
                .andDo(print())
                .andExpect(jsonPath("$[0].postInfo").value("person"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostsByIdEmpty() throws Exception {

        when(personJPA.findById(1L)).thenReturn(java.util.Optional.ofNullable(person));

        when(person.getPosts()).thenReturn(Arrays.asList());

        this.mockMvc.perform(get("/getPost/1"))
                .andDo(print())
                .andExpect(content().string("[]"))
                .andExpect(status().isOk());
    }

}
