package xyz.hyunto.spring5.master.todo.exception.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import xyz.hyunto.spring5.master.todo.exception.TodoNotFoundException;
import xyz.hyunto.spring5.master.todo.exception.model.ExceptionResponse;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> todoNotFound(TodoNotFoundException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                e.getMessage(),
                "Any details you would want to add");
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}
