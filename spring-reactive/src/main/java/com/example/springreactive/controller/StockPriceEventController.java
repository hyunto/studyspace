package com.example.springreactive.controller;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.springreactive.model.Stock;
import com.example.springreactive.repository.StockMongoReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class StockPriceEventController {

	@Autowired
	private StockMongoReactiveCrudRepository repository;

	@GetMapping("/stocks/price/{stockCode}")
	public Flux<String> retriveStockPriceHardcoded(@PathVariable("stockCode") String stockCode) {
		return Flux.interval(Duration.ofSeconds(5))
			.map(l -> getCurrentDate() + " : " + getRandomNumber(100, 125))
			.log();
	}

	private String getCurrentDate() {
		return (new Date()).toString();
	}

	private int getRandomNumber(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	@GetMapping("/stocks")
	public Flux<Stock> list() {
		return repository.findAll().log();
	}

	@GetMapping("/stocks/{code}")
	public Mono<Stock> findById(@PathVariable("code") String code) {
		return repository.findById(code).log();
	}
}
