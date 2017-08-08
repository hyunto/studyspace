package com.spring.boot.journal.domain;

import lombok.*;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hyunsoo0813 on 2017. 8. 8..
 */
@Entity
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private Date created;

    @Getter
    @Setter
    private String summary;

    @Transient
    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    public String getCreatedAsShort() {
        return format.format(created);
    }

    @Builder
    public Journal(String title, String date, String summary) throws ParseException {
        this.title = title;
        this.created = format.parse(date);
        this.summary = summary;
    }
}
