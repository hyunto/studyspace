package xyz.hyunto.spring5.master.todo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@NoArgsConstructor
public class Todo {

    private int id;
    @NotNull
    private String user;

    @Size(min = 9, message = "Enter at least 10 Characters.")
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
