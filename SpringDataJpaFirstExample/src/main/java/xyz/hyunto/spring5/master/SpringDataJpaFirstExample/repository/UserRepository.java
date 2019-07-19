package xyz.hyunto.spring5.master.SpringDataJpaFirstExample.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import xyz.hyunto.spring5.master.SpringDataJpaFirstExample.entity.User;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    List<User> findByName(String name);
    List<User> findByName(String name, Sort sort);
    List<User> findByName(String name, Pageable pageable);
    Long countByName(String name);
    Long deleteByName(String name);
    List<User> removeByName(String name);
    List<User> findByTodosTitle(String title);

    User findFirstByName(String name);
    User findTopByName(String name);

    List<User> findTop3ByName(String name);
    List<User> findFirst3ByName(String name);

    @Query("select u from User u where u.name = ?1")
    List<User> findUsersByNameUsingQuery(String name);

    @Query("select u from User u where u.name = :name")
    List<User> findUsersByNameUsingNamedParameters(@Param("name") String name);

    List<User> findUsersWithNameUsingNamedQuery(String name);

    @Query(value = "SELECT * FROM USERS WHERE u.name = ?1", nativeQuery = true)
    List<User> findUsersByNameNativeQuery(String name);
}
