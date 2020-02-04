package com.example.spring.consumer.serviceconsumer.proxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/* Before */
//@FeignClient(name = "microservice-a")
/* After */
@FeignClient(name = "zuul-api-gateway")
@RibbonClient(name = "microservice-a")
public interface RandomServiceProxy {

    /* Before */
//    @GetMapping("/random")
    /* After */
    @GetMapping("/microservice-a/random")
    public List<Integer> getRandomNumbers();
}
