package xyz.hyunto.clientimplicit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

	@GetMapping("/")
	public String client() {
		return "client";
	}

	@GetMapping("/callback")
	public String callback() {
		return "callback_page";
	}
}
