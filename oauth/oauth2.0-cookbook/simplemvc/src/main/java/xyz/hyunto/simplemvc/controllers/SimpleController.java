package xyz.hyunto.simplemvc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SimpleController {

	@GetMapping("/message")
	public ResponseEntity<String> getMessage() {
		return ResponseEntity.ok("Hello!");
	}
}
