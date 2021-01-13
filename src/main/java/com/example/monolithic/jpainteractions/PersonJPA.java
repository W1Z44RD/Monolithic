package com.example.monolithic.jpainteractions;
import com.example.monolithic.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;

public interface PersonJPA extends JpaRepository<Person, Long>{
    Collection<Person> findByName(String Name);

    Collection<Person> findByJob(String Job);

    Collection<Person> findByNameAndJob(String Name, String Job);
}