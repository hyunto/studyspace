package com.hyunto.startspringbootbook;

import com.hyunto.startspringbootbook.domain.Board;
import com.hyunto.startspringbootbook.domain.QBoard;
import com.hyunto.startspringbootbook.persistence.BoardRepository;
import com.querydsl.core.BooleanBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
		Pageable paging = new PageRequest(0, 10);

		Collection<Board> results = boardRepository.findByBnoGreaterThanOrderByBnoDesc(0L, paging);

		results.forEach(board -> System.out.println(board));
	}

	@Test
    public void testBnoPagingSort() {
	    Pageable paging = new PageRequest(0, 10, Sort.Direction.ASC, "bno");

	    Page<Board> results = boardRepository.findByBnoGreaterThan(0L, paging);

        System.out.println("PAGE SIZE : " + results.getSize());
        System.out.println("TOTAL PAGES : " + results.getTotalPages());
        System.out.println("TOTAL COUNT : " + results.getTotalElements());
        System.out.println("NEXT : " + results.nextPageable());

        List<Board> list = results.getContent();

	    list.forEach(board -> System.out.println(board));
    }

    @Test
    public void testFindByTitle_Using_QueryAnnotation() {
	    boardRepository.findByTitle("17")
                .forEach(board -> System.out.println(board));
    }

    @Test
    public void testFindByContent_Using_QueryAnnotation() {
	    boardRepository.findByContent("119")
                .forEach(board -> System.out.println(board));
    }

    @Test
    public void testFindByWriter2() {
	    boardRepository.findByWriter2("user07")
                .forEach(board -> System.out.println(board));
    }

    @Test
    public void testFindByTitle2() {
	    boardRepository.findByTitle2("17")
                .forEach(arr -> System.out.println(Arrays.toString(arr)));
    }

    @Test
    public void testFindByTitle3() {
        boardRepository.findByTitle3("19")
                .forEach(arr -> System.out.println(Arrays.toString(arr)));
    }

    @Test
    public void testByPaging() {
	    Pageable pageable = new PageRequest(0, 10);
	    boardRepository.findBypage(pageable)
                .forEach(board -> System.out.println(board));
    }

    @Test
    public void testPredicate() {
	    String type = "t";
	    String keyword = "19";

        BooleanBuilder builder = new BooleanBuilder();

        QBoard board = QBoard.board;

        if (type.equals("t")) {
            builder.and(board.title.like("%" + keyword + "%"));
        }

        // bno > 0
        builder.and(board.bno.gt(0L));

        Pageable pageable = new PageRequest(0, 10);

        Page<Board> result = boardRepository.findAll(builder, pageable);

        System.out.println("PAGE SIZE : " + result.getSize());
        System.out.println("TOTAL PAGES : " + result.getTotalPages());
        System.out.println("TOTAL COUNT : " + result.getTotalElements());
        System.out.println("NEXT : " + result.nextPageable());

        List<Board> list = result.getContent();

        list.forEach(board1 -> System.out.println(board1));
    }

}
