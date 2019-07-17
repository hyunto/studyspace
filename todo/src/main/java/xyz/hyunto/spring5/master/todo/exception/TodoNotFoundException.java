package xyz.hyunto.spring5.master.todo.exception;

public class TodoNotFoundException extends RuntimeException {

    public TodoNotFoundException(String msg) {
        super(msg);
    }

}
