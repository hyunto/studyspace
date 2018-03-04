package com.hyunto.startspringboot.part3.persistence;

import com.hyunto.startspringboot.part3.domain.WebBoard;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest
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

	@Test
	public void testList1() {
		Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");

        Page<WebBoard> result = webBoardRepository.findAll(webBoardRepository.makePredicate(null, null), pageable);

        log.info("PAGE: " + result.getPageable());

        log.info("-------------------------------------");

        result.getContent().forEach(webBoard -> {
            log.info("" + webBoard);
        });
	}

	@Test
    public void testList2() {
	    Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");

	    Page<WebBoard> result = webBoardRepository.findAll(webBoardRepository.makePredicate("t", "10"), pageable);

	    log.info("PAGE: " + result.getPageable());

	    log.info("----------------------------------------");
	    result.getContent().forEach(webBoard -> {
	        log.info("" + webBoard);
        });
    }
}
