package com.hyunto.startspringboot.part3.persistence;

import com.hyunto.startspringboot.part3.domain.WebBoard;
import groovy.util.logging.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit
public class WebBoardRepositoryTests {

	@Autowired
	private WebBoardRepository webBoardRepository;

	// 더미 데이터 추가
	@Test
	public void insertBoardDummies() {
		IntStream.range(0, 300).forEach(i -> {
			WebBoard board = new WebBoard();

			board.setTitle("Sample Board Title " + i);
			board.setContent("Content Sample..." + i + " of Board");
			board.setWriter("user0" + (i % 10));

			webBoardRepository.save(board);
		});
	}
}
