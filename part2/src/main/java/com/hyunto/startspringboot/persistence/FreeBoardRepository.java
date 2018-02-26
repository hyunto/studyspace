package com.hyunto.startspringboot.persistence;

import com.hyunto.startspringboot.domain.FreeBoard;
import org.springframework.data.repository.CrudRepository;

public interface FreeBoardRepository extends CrudRepository<FreeBoard, Long> {

}
