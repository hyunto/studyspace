package com.hyunto.startspringbootbook.persistence;

import com.hyunto.startspringbootbook.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface BoardRepository extends CrudRepository<Board, Long> {

	public List<Board> findBoardByTitle(String title);

	public Collection<Board> findByWriter(String writer);

	public Collection<Board> findByWriterContaining(String writer);

	public List<Board> findByTitleContainingOrContentContaining(String title, String content);

	public List<Board> findByTitleContainingAndBnoGreaterThan(String title, Long id);

	public List<Board> findByBnoGreaterThanOrderByBnoDesc(Long id, Pageable paging);

	public Page<Board> findByBnoGreaterThan(Long id, Pageable paging);

}
