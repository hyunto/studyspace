package xyz.hyunto.spring5.master.todo.service;

import org.springframework.stereotype.Service;
import xyz.hyunto.spring5.master.todo.model.Todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private static List<Todo> todos = new ArrayList<>();
    private static int todoCount = 3;

    static {
        todos.add(new Todo(1, "Jack", "Learn Spring MVC", new Date(), false));
        todos.add(new Todo(2, "Jack", "Learn Struts", new Date(), false));
        todos.add(new Todo(3, "Jill", "Learn Hibernate", new Date(), false));
    }

    public List<Todo> retrieveTodos(String user) {
        /* Before Java 8 */
//        List<Todo> filteredTodos = new ArrayList<>();
//        for (Todo todo : todos) {
//            if (todo.getUser().equals(user)) {
//                filteredTodos.add(todo);
//            }
//        }
//        return filteredTodos;

        /* After Java 8 */
        return todos.stream()
                .filter(todo -> todo.getUser().equals(user))
                .collect(Collectors.toList());
    }

    public Todo addTodo(String name, String desc, Date targetDate, boolean isDone) {
        Todo todo = new Todo(todoCount + 1, name, desc, targetDate, isDone);
        todos.add(todo);
        return todo;
    }

    public Todo retrieveTodo(int id) {
        return todos.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
