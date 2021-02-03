package com.example.monolithic.controllers;

import com.example.monolithic.entities.Person;
import com.example.monolithic.entities.Post;
import com.example.monolithic.jpainteractions.PersonJPA;
import com.example.monolithic.jpainteractions.PostJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.Optional;

//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Validated
public class MonolithicController {

    @Autowired
    private PersonJPA personJPA;
    @Autowired
    private PostJPA postJPA;

    private final Logger LOG = LoggerFactory.getLogger(MonolithicController.class);


    @GetMapping("/Monolithic")
    public ResponseEntity<String> getApplicationName(){
        LOG.info("Monolithic API Called -");
        return new ResponseEntity<String>("Monolithic API", HttpStatus.OK);
    }

    @GetMapping("/all")
    public Collection<Person> getAll(){
        LOG.info("Return all");
        return personJPA.findAll();
    }

//    @GetMapping("person/{id}")
//    public ResponseEntity<MappingJacksonValue> getById(@PathVariable Long id) throws Exception {
//
//        Optional<Person> p = personJPA.findById(id);
//
//        if (p.isEmpty()){
//            throw new Exception("Cannot find person");
//        }
//        Person p1 = p.get();
//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name");
//        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("BeanToFilter", filter);
//        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(p);
//        mappingJacksonValue.setFilters(filterProvider);
//
//        LOG.info("Return by id");
//        return new ResponseEntity<MappingJacksonValue>(mappingJacksonValue, HttpStatus.OK);
//    }

    @DeleteMapping("/delete/{sId}")
    public void deleteById(@PathVariable String sId) {
        try {
            Long id = Long.parseLong(sId);
            personJPA.deleteById(id);
        }
        catch (NumberFormatException E){
            throw new NumberFormatException("Invalid ID");
        }
        LOG.info("Delete by id");

    }

    @PostMapping("/add")
    public ResponseEntity<Person> addNew(@RequestBody Person newPerson) throws Exception{
        try {
            personJPA.save(newPerson);
        }
        catch (Exception e){
            throw new Exception("Invalid person passed");
        }
        return new ResponseEntity<Person>(newPerson, HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/post")
    public ResponseEntity<Post> addNew(@PathVariable String userId, @RequestBody Post newPost) {
        try {
            Long id = Long.parseLong(userId);
            Optional<Person> person1 = personJPA.findById(id);
            if (person1.isPresent()){
                Person p1 = person1.get();
                newPost.setPerson(p1);
                postJPA.save(newPost);
                return new ResponseEntity<Post>(newPost, HttpStatus.CREATED);
            }
            else {
                throw new Exception("Person doesn't exist");
            }
        }
        catch (Exception e){
            throw new NumberFormatException("Invalid id");
        }
    }

    @GetMapping("/greeting")
        public @ResponseBody String greet(){
            return "Hello World";
        }


    @PutMapping("/editName/{sId}")
    public void editById(@PathVariable String sId, @RequestBody Person newName) throws Exception {
        try {
        Long id = Long.parseLong(sId);
            Optional<Person> edit = personJPA.findById(id);

            if (edit.isEmpty()){
                throw new Exception("Invalid person");
            }
            Person newGuy = edit.get();
            newGuy.setName(newName.getName());
            personJPA.save(newGuy);
        }
        catch (Exception E){
            throw new NumberFormatException("Invalid id");
        }
    }

    @GetMapping("/getPosts")
    public Collection<Post> getPosts(){
        return postJPA.findAll();
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
    public Collection<Post> getPostById(@PathVariable Long id) throws Exception {
        Optional<Person>  person = personJPA.findById(id);
        if (person.isEmpty()){
            throw new Exception("Person not found");
        }
        return person.get().getPosts();
    }

    @GetMapping(path = "/CheesyFries")
    public ResponseEntity<Person> testCase(){
        return new ResponseEntity<Person>(new Person("Hello", "World"), HttpStatus.OK);
    }

    @GetMapping("/uriParams")
    public Person getParams(@PathParam("name") String name, @PathParam("job") String job){
        return new Person(name, job);
    }
}
