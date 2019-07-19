package xyz.hyunto.spring5.master.SpringDataJpaFirstExample.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import xyz.hyunto.spring5.master.SpringDataJpaFirstExample.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
}
