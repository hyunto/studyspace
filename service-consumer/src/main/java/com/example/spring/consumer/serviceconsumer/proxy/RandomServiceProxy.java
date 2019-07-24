package com.example.spring.consumer.serviceconsumer.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "microservice-a", url = "localhost:30010")
public interface RandomServiceProxy {

    @GetMapping("/random")
    public List<Integer> getRandomNumbers();
}
