package xyz.hyunto.spring5.master.SpringDataJpaFirstExample.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.hyunto.spring5.master.SpringDataJpaFirstExample.entity.Todo;

public interface TodoRepository extends CrudRepository<Todo, Long> {
}
