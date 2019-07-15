package xyz.hyunto.spring5.master.todo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class Todo {

    private int id;
    private String user;

    private String desc;

    private Date targetDate;
    private boolean isDone;

    public Todo(int id, String user, String desc, Date targetDate, boolean isDone) {
        this.id = id;
        this.user = user;
        this.desc = desc;
        this.targetDate = targetDate;
        this.isDone = isDone;
    }

}
