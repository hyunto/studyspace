package com.example.springreactive.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Controller
public class StockPriceController {

	@Bean
	public RouterFunction<ServerResponse> htmlRouter(@Value("classpath:/static/stock-price.html") final Resource html) {
		return route(GET("/"), request -> ok().contentType(MediaType.TEXT_HTML).syncBody(html));
	}


}
