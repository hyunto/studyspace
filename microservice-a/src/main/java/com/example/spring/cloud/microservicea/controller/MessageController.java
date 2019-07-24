package com.example.spring.cloud.microservicea.controller;

import com.example.spring.cloud.microservicea.property.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RefreshScope
@RestController
public class MessageController {

    @Autowired
    private ApplicationConfiguration configuration;

    @GetMapping("/message")
    public Map<String, String> welcome() {
        Map<String, String> map = new HashMap<>();
        map.put("message", configuration.getMessage());
        return map;
    }

}
