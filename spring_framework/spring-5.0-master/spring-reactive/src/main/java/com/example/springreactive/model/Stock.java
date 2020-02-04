package com.example.springreactive.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document
public class Stock {

	private String code;
	private String name;
	private String description;

}
