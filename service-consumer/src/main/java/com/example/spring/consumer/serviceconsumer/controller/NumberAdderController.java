package com.example.spring.consumer.serviceconsumer.controller;

import com.example.spring.consumer.serviceconsumer.proxy.RandomServiceProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class NumberAdderController {

    @Value("${number.service.url}")
    private String numberServiceUrl;

    @Autowired
    private RandomServiceProxy randomServiceProxy;

    @GetMapping("/add")
    public Long add() {
        long sum = 0;

        /* before using feign */
//        ResponseEntity<Integer[]> responseEntity = new RestTemplate().getForEntity(numberServiceUrl, Integer[].class);
//        Integer[] numbers = responseEntity.getBody();

        /* after using feign */
        List<Integer> numbers = randomServiceProxy.getRandomNumbers();
        log.warn("Get numbers from microservice-a : {}", numbers.toString());

        for (int number : numbers) {
            sum += number;
        }
        log.warn("Returning {}", sum);
        return sum;
    }
}
