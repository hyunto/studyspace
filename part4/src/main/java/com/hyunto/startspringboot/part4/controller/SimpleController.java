package com.hyunto.startspringboot.part4.controller;

import lombok.extern.java.Log;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log
@Controller
public class SimpleController {

	@GetMapping("/")
	public String index() {
		log.info("index");
		return "index";
	}

	@RequestMapping("/guest")
	public void forGuest() {
		log.info("guest");
	}

	@RequestMapping("/manager")
	public void forManager() {
		log.info("manager");
	}

	@RequestMapping("/admin")
	public void forAdmin() {
		log.info("admin");
	}

	@Secured(value = {"ROLE_ADMIN"})
	@RequestMapping("/adminSecret")
	public void forAdminSecret() {
		log.info("admin secret");
	}

	@Secured(value = {"ROLE_MANAGER"})
	@GetMapping("/managerSecret")
	public void forManagerSecret() {
		log.info("manager secret");
	}
}
