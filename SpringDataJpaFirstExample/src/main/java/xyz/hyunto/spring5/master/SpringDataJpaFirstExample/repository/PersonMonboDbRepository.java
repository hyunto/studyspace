package xyz.hyunto.spring5.master.SpringDataJpaFirstExample.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import xyz.hyunto.spring5.master.SpringDataJpaFirstExample.entity.Person;

import java.util.List;

public interface PersonMonboDbRepository extends MongoRepository<Person, String> {

    List<Person> findByName(String name);
    Long countByName(String name);

}
