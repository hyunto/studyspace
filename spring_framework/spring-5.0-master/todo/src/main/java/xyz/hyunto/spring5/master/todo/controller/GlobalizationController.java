package xyz.hyunto.spring5.master.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class GlobalizationController {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @GetMapping("/welcome-internationalized")
    public String msg(@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("welcome.message", null, locale);
    }
}
