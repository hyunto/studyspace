package com.hyunto.startspringboot.persistence;

import com.hyunto.startspringboot.domain.FreeBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FreeBoardRepository extends CrudRepository<FreeBoard, Long> {

    List<FreeBoard> findByBnoGreaterThan(Long bno, Pageable page);

    @Query("SELECT b.bno, b.title, COUNT(r) FROM FreeBoard AS b LEFT OUTER JOIN b.replies AS r WHERE b.bno > 0 GROUP BY b")
    List<Object[]> getPage(Pageable page);
}
