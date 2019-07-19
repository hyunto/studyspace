package xyz.hyunto.spring5.master.SpringDataJpaFirstExample.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String userid;

	private String name;

	@OneToMany(mappedBy = "user")
	private List<Todo> todos;

}
