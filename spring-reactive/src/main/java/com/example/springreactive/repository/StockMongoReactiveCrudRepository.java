package com.example.springreactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.example.springreactive.model.Stock;

public interface StockMongoReactiveCrudRepository extends ReactiveCrudRepository<Stock, String> {
}
