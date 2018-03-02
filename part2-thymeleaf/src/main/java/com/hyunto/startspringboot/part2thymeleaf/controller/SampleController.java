package com.hyunto.startspringboot.part2thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {

	@GetMapping("/sample1")
	public void sample1(Model model) {
//		model.addAttribute("greeting", "Hello World");
		model.addAttribute("greeting", "안녕하세요");
	}
}
