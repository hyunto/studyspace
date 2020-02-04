package com.example.springreactive;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.example.springreactive.model.Stock;
import com.example.springreactive.repository.StockMongoReactiveCrudRepository;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class SpringReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(StockMongoReactiveCrudRepository mongoRepository) {
		return (p) -> {
			mongoRepository.deleteAll().block();
			mongoRepository.save(new Stock("IBM", "IBM Corporation", "Desc")).block();
			mongoRepository.save(new Stock("GGL", "Google", "Desc")).block();
			mongoRepository.save(new Stock("MST", "Microsoft", "Desc")).block();
		};
	}

}
