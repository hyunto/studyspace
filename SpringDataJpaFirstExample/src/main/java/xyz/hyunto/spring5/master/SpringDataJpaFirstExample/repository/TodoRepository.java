package xyz.hyunto.spring5.master.SpringDataJpaFirstExample.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.hyunto.spring5.master.SpringDataJpaFirstExample.entity.Todo;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Long> {

    List<Todo> findByTitleAndDescription(String title, String description);
    List<Todo> findDistinctTodoByTitleOrDescription(String title, String description);
    List<Todo> findByTitleIgnoreCase(String title, String description);
    List<Todo> findByTitleOrOrderByIdDesc(String lastName);
    List<Todo> findByIsDoneTrue(String lastName);

}
