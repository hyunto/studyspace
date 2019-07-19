package xyz.hyunto.spring5.master.SpringDataJpaFirstExample.entity;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Id;

@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Person {

    @Id
    private String id;
    @NonNull
    private String name;

}
