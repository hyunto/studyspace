package com.hyunto.startspringboot.part4finalproject.controller;

import com.hyunto.startspringboot.part4finalproject.domain.Member;
import com.hyunto.startspringboot.part4finalproject.persistence.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log
@Controller
@RequestMapping("/member/")
public class MemberController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MemberRepository memberRepository;

	@GetMapping("/join")
	public void join() {

	}

	@PostMapping("/join")
	public String joinPost(@ModelAttribute("member") Member member) {
		log.info("MEMBER : " + member);

		String encrypPw = passwordEncoder.encode(member.getUpw());

		log.info("en: " + encrypPw);

		member.setUpw(encrypPw);

		memberRepository.save(member);

		return "/member/joinResult";
	}

}
