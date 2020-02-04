package xyz.hyunto.spring5.master.springbootexample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import xyz.hyunto.spring5.master.springbootexample.bean.WelcomeBean;

@RestController
public class BasicController {

	private static final String HELLO_WORLD_TEMPLATE = "Hello World, %s!";

	@GetMapping("/welcome")
	public String welcome() {
		return "Hello World";
	}

	@GetMapping("/welcome-with-object")
	public WelcomeBean welcomeWithObject() {
		return new WelcomeBean("Hello World");
	}

	@GetMapping("/welcome-with-parameter/name/{name}")
	public WelcomeBean welcomeBeanWithParameter(@PathVariable String name) {
		return new WelcomeBean(String.format(HELLO_WORLD_TEMPLATE, name));
	}

}
