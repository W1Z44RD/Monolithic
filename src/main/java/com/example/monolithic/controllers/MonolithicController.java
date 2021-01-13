package com.example.monolithic.controllers;

import com.example.monolithic.entities.Person;
import com.example.monolithic.entities.Post;
import com.example.monolithic.jpainteractions.PersonJPA;
import com.example.monolithic.jpainteractions.PostJPA;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class MonolithicController {

    @Autowired
    private PersonJPA personJPA;
    @Autowired
    private PostJPA postJPA;

    private Logger LOG = LoggerFactory.getLogger(MonolithicController.class);


    @GetMapping("/Monolithic")
    public ResponseEntity<String> getApplicationName(){
        LOG.info("Monolithic API Called -");
        return new ResponseEntity<String>("Monolithic API", HttpStatus.OK);
    }

    @GetMapping("/all")
    public Collection<Person> getAll(){
        Collection<Person> people = personJPA.findAll();
        LOG.info("Return all");
        return people;
    }

    @GetMapping("person/{id}")
    public ResponseEntity<MappingJacksonValue> getById(@PathVariable Long id) throws Exception {

        Optional<Person> p = personJPA.findById(id);

        if (p.isEmpty()){
            throw new Exception("Cannot find person");
        }
        Person p1 = p.get();
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("BeanToFilter", filter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(p);
        mappingJacksonValue.setFilters(filterProvider);

        LOG.info("Return by id");
        return new ResponseEntity<MappingJacksonValue>(mappingJacksonValue, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
        LOG.info("Delete by id");
        personJPA.deleteById(id);
    }

    @PostMapping("/add")
    public void addNew(@RequestBody Person newPerson){
        personJPA.save(newPerson);
    }

    @PutMapping("/editName/{id}")
    public void editById(@PathVariable Long id, @RequestBody Person newName){
        Optional<Person> edit = personJPA.findById(id);
        if (edit.isEmpty()){
            return;
        }
        Person newGuy = edit.get();
        newGuy.setName(newName.getName());
        personJPA.save(newGuy);
    }

    @GetMapping("/getByName/{name}")
    public Collection<Person> getByName(@PathVariable String name){
        return personJPA.findByName(name);
    }

    @GetMapping("/getByJob/{job}")
    public Collection<Person> getByJob(@PathVariable String job){
        return personJPA.findByJob(job);
    }

    @GetMapping("/getByNameAndJob/{name}/{job}")
    public Collection<Person> getByNameAndJob(@PathVariable String name, @PathVariable String job){
        return personJPA.findByNameAndJob(name, job);
    }

//    @GetMapping("/help")
//    public Person help(){
//        Person person = new Person("Need", "Help");
//        person.add(linkTo(methodOn(MonolithicController.class).getByName("Harrison")).withSelfRel());
//        return person;
//    }

    @GetMapping("/getPost/{id}")
    public Collection<Post> getPostById(@PathVariable Long id){
        Person person = personJPA.findById(id).get();
        Collection<Post> posts = person.getPosts();
        return posts;
    }
}
