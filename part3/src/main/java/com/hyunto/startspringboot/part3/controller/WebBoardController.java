package com.hyunto.startspringboot.part3.controller;

import lombok.extern.java.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log
@Controller
@RequestMapping("/boards")
public class WebBoardController {

	@GetMapping("/list")
	public void list(
	        @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC, sort = "bno") Pageable page
    ) {

		log.info("list() called..." + page);
	}

}
