package com.hyunto.startspringbootbook.persistence;

import com.hyunto.startspringbootbook.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Long>, QueryDslPredicateExecutor<Board> {

	List<Board> findBoardByTitle(String title);

	Collection<Board> findByWriter(String writer);

	Collection<Board> findByWriterContaining(String writer);

	List<Board> findByTitleContainingOrContentContaining(String title, String content);

	List<Board> findByTitleContainingAndBnoGreaterThan(String title, Long id);

	List<Board> findByBnoGreaterThanOrderByBnoDesc(Long id, Pageable paging);

	Page<Board> findByBnoGreaterThan(Long id, Pageable paging);

	@Query("SELECT b FROM Board b WHERE b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	List<Board> findByTitle(String title);

	@Query("SELECT b FROM Board b WHERE b.content LIKE %:content% AND b.bno > 0 ORDER BY b.bno DESC")
	List<Board> findByContent(@Param("content") String content);

	@Query("SELECT b FROM #{#entityName} b WHERE b.writer LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	List<Board> findByWriter2(String writer);

	@Query("SELECT b.bno, b.title, b.writer, b.regdate FROM Board b WHERE b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	List<Object[]> findByTitle2(String title);

	@Query(value = "SELECT bno, title, writer FROM tbl_boards WHERE title LIKE CONCAT('%', ?1, '%') AND bno > 0 ORDER BY bno DESC",
            nativeQuery = true)
	List<Object[]> findByTitle3(String title);

	@Query("SELECT b FROM Board b WHERE b.bno > 0 ORDER BY b.bno DESC")
	List<Board> findBypage(Pageable pageable);

}
