package xyz.hyunto.spring5.master.todo.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import xyz.hyunto.spring5.master.todo.exception.TodoNotFoundException;
import xyz.hyunto.spring5.master.todo.model.Todo;
import xyz.hyunto.spring5.master.todo.service.TodoService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoService todoService;

    @ApiOperation(
        value = "이름으로 유저의 모든 todos 검색",
        notes = "일치하는 todos 리스트가 반환된다. 현재 페이징은 지원되지 않는다",
        response = Todo.class,
        responseContainer = "List",
        produces = "application/json"
    )
    @GetMapping("/users/{name}/todos")
    public List<Todo> retrieveTodos(@PathVariable String name) {
        return todoService.retrieveTodos(name);
    }

    @GetMapping("/users/{name}/todos/{id}")
    public Resource<Todo> retrieveTodo(@PathVariable String name,
                             @PathVariable int id) {
        Todo todo = todoService.retrieveTodo(id);
        if (todo == null) {
            throw new TodoNotFoundException("Todo Not Foud");
        }

        Resource<Todo> todoResource = new Resource<>(todo);
        ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(this.getClass()).retrieveTodos(name));
        todoResource.add(linkTo.withRel("parent"));

        return todoResource;
    }

    @PostMapping("/users/{name}/todos")
    public ResponseEntity<?> add(@PathVariable String name,
                          @Valid @RequestBody Todo todo) {
        Todo createdTodo = todoService.addTodo(name, todo.getDesc(), todo.getTargetDate(), todo.isDone());
        if (createdTodo == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTodo.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /* 06-1. 예외 처리 */
    @GetMapping("/users/dummy-service")
    public Todo errorService() {
        throw new RuntimeException("Some Exception Occurred");
    }

}
