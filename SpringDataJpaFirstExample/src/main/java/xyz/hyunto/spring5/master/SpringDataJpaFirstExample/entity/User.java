package xyz.hyunto.spring5.master.SpringDataJpaFirstExample.entity;

import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@ToString(exclude = "todos")
@NamedQuery(
		name = "User.findUsersWithNameUsingNamedQuery",
		query = "select u from User u where u.name = ?1"
)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String userid;

	private String name;

	@OneToMany(mappedBy = "user")
	private List<Todo> todos;

}
