package com.hyunto.startspringboot.part3.controller;

import com.hyunto.startspringboot.part3.domain.WebBoard;
import com.hyunto.startspringboot.part3.persistence.WebBoardRepository;
import com.hyunto.startspringboot.part3.vo.PageMaker;
import com.hyunto.startspringboot.part3.vo.PageVO;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log
@Controller
@RequestMapping("/boards")
public class WebBoardController {

    @Autowired
    private WebBoardRepository webBoardRepository;

	@GetMapping("/list")
	public void list(PageVO pageVO, Model model) {
        Pageable page = pageVO.makePageable(0, "bno");

        Page<WebBoard> result = webBoardRepository.findAll(webBoardRepository.makePredicate(null, null), page);

        log.info("" + page);
        log.info("" + result);

        log.info("TOTAL PAGE NUMBER: " + result.getTotalPages());

        model.addAttribute("result", new PageMaker(result));
	}

}
