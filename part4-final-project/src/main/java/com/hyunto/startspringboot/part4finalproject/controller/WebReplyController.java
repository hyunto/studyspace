package com.hyunto.startspringboot.part4finalproject.controller;

import com.hyunto.startspringboot.part4finalproject.domain.WebBoard;
import com.hyunto.startspringboot.part4finalproject.domain.WebReply;
import com.hyunto.startspringboot.part4finalproject.persistence.WebReplyRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Log
@RestController
@RequestMapping("/replies/*")
public class WebReplyController {

	@Autowired
	private WebReplyRepository webReplyRepository;

	@Secured(value = {"ROLE_BASIC", "ROLE_MANAGER", "ROLE_ADMIN"})
	@Transactional
	@PostMapping("/{bno}")
	public ResponseEntity<List<WebReply>> addReply(@PathVariable("bno") Long bno,
												   @RequestBody WebReply webReply) {
		log.info("addReply..........");
		log.info("BNO: " +bno);
		log.info("REPLY: " + webReply);

		WebBoard webBoard = new WebBoard();
		webBoard.setBno(bno);

		webReply.setBoard(webBoard);

		webReplyRepository.save(webReply);

		return new ResponseEntity<>(this.getListByBoard(webBoard), HttpStatus.CREATED);
	}

	private List<WebReply> getListByBoard(WebBoard webBoard) throws RuntimeException {
		log.info("getListByBoard: " + webBoard);
		return webReplyRepository.getRepliesOfBoard(webBoard);
	}

    @Secured(value = {"ROLE_BASIC", "ROLE_MANAGER", "ROLE_ADMIN"})
	@Transactional
	@DeleteMapping("/{bno}/{rno}")
	public ResponseEntity<List<WebReply>> remove(@PathVariable("bno") Long bno,
												 @PathVariable("rno") Long rno) {
		log.info("delete reply: " + rno);

		webReplyRepository.deleteById(rno);

		WebBoard webBoard = new WebBoard();
		webBoard.setBno(bno);

		return new ResponseEntity<>(this.getListByBoard(webBoard), HttpStatus.OK);
	}

    @Secured(value = {"ROLE_BASIC", "ROLE_MANAGER", "ROLE_ADMIN"})
	@Transactional
	@PutMapping("/{bno}")
	public ResponseEntity<List<WebReply>> modify(@PathVariable("bno") Long bno,
												 @RequestBody WebReply webReply) {
		log.info("modify reply: " + webReply);

		webReplyRepository.findById(webReply.getRno()).ifPresent(origin -> {
			origin.setReplyText(webReply.getReplyText());

			webReplyRepository.save(origin);
		});

		WebBoard webBoard = new WebBoard();
		webBoard.setBno(bno);

		return new ResponseEntity<>(this.getListByBoard(webBoard), HttpStatus.CREATED);
	}

	@GetMapping("/{bno}")
	public ResponseEntity<List<WebReply>> getReplies(@PathVariable("bno") Long bno) {
		log.info("get All Replies..........");

		WebBoard webBoard = new WebBoard();
		webBoard.setBno(bno);
		return new ResponseEntity<>(this.getListByBoard(webBoard), HttpStatus.OK);
	}
}
