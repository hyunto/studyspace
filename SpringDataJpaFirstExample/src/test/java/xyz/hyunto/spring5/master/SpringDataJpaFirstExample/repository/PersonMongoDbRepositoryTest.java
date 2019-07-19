package xyz.hyunto.spring5.master.SpringDataJpaFirstExample.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.hyunto.spring5.master.SpringDataJpaFirstExample.entity.Person;

@DataMongoTest
@RunWith(SpringRunner.class)
public class PersonMongoDbRepositoryTest {

    @Autowired
    private PersonMonboDbRepository personRepository;

    @Test
    public void simpleTest() {
        personRepository.deleteAll();
        personRepository.save(new Person("name1"));
        personRepository.save(new Person("name2"));
        personRepository.findAll().forEach(System.out::println);
        System.out.println(personRepository.findByName("name1"));
        System.out.println(personRepository.count());
    }
}
