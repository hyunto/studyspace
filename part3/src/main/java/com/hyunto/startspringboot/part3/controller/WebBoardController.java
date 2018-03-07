package com.hyunto.startspringboot.part3.controller;

import com.hyunto.startspringboot.part3.domain.WebBoard;
import com.hyunto.startspringboot.part3.persistence.CustomCrudRepository;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log
@Controller
@RequestMapping("/boards")
public class WebBoardController {

    @Autowired
    private WebBoardRepository webBoardRepository;

    @Autowired
    private CustomCrudRepository customCrudRepository;

	@GetMapping("/list")
	public void list(@ModelAttribute("pageVO") PageVO vo, Model model) {
        Pageable page = vo.makePageable(0, "bno");

//        Page<WebBoard> result = webBoardRepository.findAll(webBoardRepository.makePredicate(vo.getType(), vo.getKeyword()), page);
        Page<Object[]> result = customCrudRepository.getCustomPage(vo.getType(), vo.getKeyword(), page);

        log.info("" + page);
        log.info("" + result);

        log.info("TOTAL PAGE NUMBER: " + result.getTotalPages());

        model.addAttribute("result", new PageMaker<>(result));
	}

	@GetMapping("/register")
    public void registerGET(@ModelAttribute("vo") WebBoard webBoard) {
	    log.info("register get");
    }

	@PostMapping("/register")
    public String registerPOST(@ModelAttribute("vo") WebBoard webBoard,
                             RedirectAttributes redirectAttributes) {
	    log.info("register post");
	    log.info("" + webBoard);

	    webBoardRepository.save(webBoard);
	    redirectAttributes.addFlashAttribute("msg", "success");

	    return "redirect:/boards/list";
    }

    @GetMapping("/view")
    public void view(Long bno, @ModelAttribute("pageVO") PageVO vo, Model model) {
	    log.info("BNO : " + bno);

	    webBoardRepository.findById(bno).ifPresent(webBoard -> {
	        model.addAttribute("vo", webBoard);
        });
    }

    @GetMapping("/modify")
    public void modify(Long bno, @ModelAttribute("pageVO") PageVO vo, Model model) {
	    log.info("MODFIY BNO : " + bno);

	    webBoardRepository.findById(bno).ifPresent(webBoard -> {
	        model.addAttribute("vo", webBoard);
        });
    }

    @PostMapping("/delete")
    public String delete(Long bno, PageVO vo, RedirectAttributes redirectAttributes) {
	    log.info("DELETE BNO : " + bno);

	    webBoardRepository.deleteById(bno);

	    redirectAttributes.addFlashAttribute("msg", "success");

	    redirectAttributes.addAttribute("page", vo.getPage());
	    redirectAttributes.addAttribute("size", vo.getSize());
	    redirectAttributes.addAttribute("type", vo.getType());
	    redirectAttributes.addAttribute("keyword", vo.getKeyword());

	    return "redirect:/boards/list";
    }

    @PostMapping("modify")
    public String modifyPost(WebBoard board, PageVO vo, RedirectAttributes redirectAttributes) {
	    log.info("MODIFY Webboard : " + board);

	    webBoardRepository.findById(board.getBno()).ifPresent(webBoard -> {
	        webBoard.setTitle(board.getTitle());
	        webBoard.setContent(board.getContent());

	        webBoardRepository.save(webBoard);

	        redirectAttributes.addFlashAttribute("msg", "success");
	        redirectAttributes.addAttribute("bno", webBoard.getBno());
        });

        redirectAttributes.addAttribute("page", vo.getPage());
        redirectAttributes.addAttribute("size", vo.getSize());
        redirectAttributes.addAttribute("type", vo.getType());
        redirectAttributes.addAttribute("keyword", vo.getKeyword());

        return "redirect:/boards/list";
    }

}
