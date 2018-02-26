package com.hyunto.startspringboot;

import com.hyunto.startspringboot.domain.FreeBoard;
import com.hyunto.startspringboot.domain.FreeBoardReply;
import com.hyunto.startspringboot.persistence.FreeBoardReplyRepository;
import com.hyunto.startspringboot.persistence.FreeBoardRepository;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class FreeBoardTests {

	@Autowired
	private FreeBoardRepository freeBoardRepository;

	@Autowired
	private FreeBoardReplyRepository freeBoardReplyRepository;

	@Test
	public void insertDummy() {
		IntStream.range(1, 200).forEach(i -> {
			FreeBoard freeBoard = new FreeBoard();
			freeBoard.setTitle("Free Board..." + i);
			freeBoard.setContent("Free Content..." + i);
			freeBoard.setWriter("user" + i%10);

			freeBoardRepository.save(freeBoard);
		});
	}

	@Transactional
	@Test
	public void insertReply2Way() {
		Optional<FreeBoard> result = freeBoardRepository.findById(199L);

		result.ifPresent(freeBoard -> {
			List<FreeBoardReply> replies = freeBoard.getReplies();

			FreeBoardReply reply = new FreeBoardReply();
			reply.setReply("REPLY.....");
			reply.setReplyer("replyer00");
			reply.setBoard(freeBoard);

			replies.add(reply);

			freeBoard.setReplies(replies);

			freeBoardRepository.save(freeBoard);
		});
	}

	@Test
	public void insertReply1Way() {
		FreeBoard freeBoard = new FreeBoard();
		freeBoard.setBno(199L);

		FreeBoardReply reply = new FreeBoardReply();
		reply.setReply("REPLY.....1 WAY.....");
		reply.setReplyer("replyer01");
		reply.setBoard(freeBoard);

		freeBoardReplyRepository.save(reply);
	}
}
