package com.hyunto.startspringboot.part4finalproject.persistence;

import com.hyunto.startspringboot.part4finalproject.domain.WebBoard;
import com.hyunto.startspringboot.part4finalproject.domain.WebReply;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WebReplyRepository extends CrudRepository<WebReply, Long> {

	@Query("SELECT r FROM WebReply r WHERE r.board = ?1 AND r.rno > 0 ORDER BY r.rno ASC")
	List<WebReply> getRepliesOfBoard(WebBoard webBoard);
}
