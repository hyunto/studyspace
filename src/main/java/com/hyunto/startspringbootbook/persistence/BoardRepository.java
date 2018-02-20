package com.hyunto.startspringbootbook.persistence;

import com.hyunto.startspringbootbook.domain.Board;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Long> {

}
