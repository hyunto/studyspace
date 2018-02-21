package com.hyunto.startspringbootbook.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {SampleController.class})
public class SampleControllerTests {

	@Autowired
	MockMvc mock;

	@Test
	public void testHello() throws Exception{
		MvcResult result = mock.perform(get("/hello"))
				.andExpect(content().string("Hello World"))
				.andExpect(status().isOk())
				.andReturn();

		System.out.println(result.getResponse().getContentAsString());
	}
}
