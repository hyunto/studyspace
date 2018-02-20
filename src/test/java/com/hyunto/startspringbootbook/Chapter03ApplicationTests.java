package com.hyunto.startspringbootbook;

import com.hyunto.startspringbootbook.domain.Board;
import com.hyunto.startspringbootbook.persistence.BoardRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Chapter03ApplicationTests {

	@Autowired
	private BoardRepository boardRepository;

	@Before
	public void setUp() {
		Long boardCount = boardRepository.count();

		if (boardCount != 200) {
			boardRepository.deleteAll();
			for (int i = 1; i <= 200; i++) {
				Board board = new Board();
				board.setTitle("제목..." + i);
				board.setContent("내용..." + i + " 채우기");
				board.setWriter("user0" + (i % 10));
				boardRepository.save(board);
			}
		}
	}

	@Test
	public void testFindByTitle() {
		boardRepository.findBoardByTitle("제목...177")
				.forEach(board -> System.out.println(board));
	}

	@Test
	public void testFindByWriter() {
		Collection<Board> results = boardRepository.findByWriter("user00");
		results.forEach(board -> System.out.println(board));
	}

	@Test
	public void testFindByWriterContaining() {
		Collection<Board> results = boardRepository.findByWriterContaining("05");
		results.forEach(board -> System.out.println(board));
	}

	@Test
	public void testFindByTitleContainingOrContentContaining() {
		boardRepository.findByTitleContainingOrContentContaining("19", "13")
				.forEach(board -> System.out.println(board));
	}

	@Test
	public void testFindByTitleContainingAndBnoGreaterThan() {
		boardRepository.findByTitleContainingAndBnoGreaterThan("5", 50L)
				.forEach(board -> System.out.println(board));
	}

	@Test
	public void testFindByBnoGreaterThanOrderByBnoDesc() {
		boardRepository.findByBnoGreaterThanOrderByBnoDesc(190L)
				.forEach(board -> System.out.println(board));
	}

}
