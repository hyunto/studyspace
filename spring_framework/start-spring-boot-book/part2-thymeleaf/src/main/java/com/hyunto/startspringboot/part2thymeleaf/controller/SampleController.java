package com.hyunto.startspringboot.part2thymeleaf.controller;

import com.hyunto.startspringboot.part2thymeleaf.model.MemberVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class SampleController {

	@GetMapping("/sample1")
	public void sample1(Model model) {
//		model.addAttribute("greeting", "Hello World");
		model.addAttribute("greeting", "안녕하세요");
	}

	@GetMapping("/sample2")
    public void sample2(Model model) {
        MemberVO vo = MemberVO.builder()
                .mno(123)
                .mid("u00")
                .mpw("p00")
                .mname("홍길동")
                .regdate(new Timestamp(System.currentTimeMillis()))
                .build();
        model.addAttribute("vo", vo);
    }

    @GetMapping("/sample3")
    public void sample3(Model model) {
        List<MemberVO> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(MemberVO.builder()
                    .mno(123)
                    .mid("u0" + i)
                    .mpw("p0" + i)
                    .mname("홍길동" + i)
                    .regdate(new Timestamp(System.currentTimeMillis()))
                    .build());
        }

        model.addAttribute("list", list);
    }

    @GetMapping("/sample4")
    public void sample4(Model model) {
	    List<MemberVO> list = new ArrayList<>();

	    for (int i = 0; i < 10; i++) {
	        list.add(MemberVO.builder()
                    .mno(i)
                    .mid("u000" + i%3)
                    .mpw("p000" + 1%3)
                    .mname("홍길동" + i)
                    .regdate(new Timestamp(System.currentTimeMillis()))
                    .build());
        }

        model.addAttribute("list", list);
    }

    @GetMapping("/sample5")
    public void sample5(Model model) {
	    String result = "SUCCESS";

	    model.addAttribute("result", result);
    }

    @GetMapping("/sample6")
    public void sample6(Model model) {
	    List<MemberVO> list = new ArrayList<>();

	    for (int i = 0; i < 10; i++) {
	        list.add(MemberVO.builder()
                    .mno(i)
                    .mid("u0" + i)
                    .mpw("p0" + i)
                    .mname("홍길동" + i)
                    .regdate(new Timestamp(System.currentTimeMillis()))
                    .build());
        }

        model.addAttribute("list", list);

	    String result = "SUCCESS";

	    model.addAttribute("result", result);
    }

    @GetMapping("/sample7")
    public void sample7(Model model) {
	    model.addAttribute("now", new Date());
	    model.addAttribute("price", 123456789);
	    model.addAttribute("title", "This is a just sample.");
	    model.addAttribute("options", Arrays.asList("AAAA", "BBB", "CCC", "DDD"));
    }

    @GetMapping("/sample8")
    public void sample8(Model model) {

    }

    @GetMapping("/sample/hello")
    public void hello() {

    }
}
