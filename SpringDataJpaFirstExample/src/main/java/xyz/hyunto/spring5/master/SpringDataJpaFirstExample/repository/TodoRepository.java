package xyz.hyunto.spring5.master.SpringDataJpaFirstExample.repository;

import org.springframework.data.repository.Repository;
import xyz.hyunto.spring5.master.SpringDataJpaFirstExample.entity.Todo;

public interface TodoRepository extends Repository<Todo, Long> {
    Iterable<Todo> findAll();
    long count();
}
