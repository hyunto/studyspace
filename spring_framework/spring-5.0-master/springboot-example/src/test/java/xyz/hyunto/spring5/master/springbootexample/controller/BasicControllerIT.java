package xyz.hyunto.spring5.master.springbootexample.controller;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.hyunto.spring5.master.springbootexample.SpringbootExampleApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(
	classes = SpringbootExampleApplication.class,
	webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class BasicControllerIT {

	private static final String LOCAL_HOST = "http://localhost";

	@LocalServerPort
	private int port;
	private TestRestTemplate template = new TestRestTemplate();

	private String createURL(String uri) {
		return LOCAL_HOST + ":" + port + uri;
	}

	@Test
	public void welcome() throws Exception {
		ResponseEntity<String> response = template.getForEntity(this.createURL("/welcome"), String.class);
		assertThat(response.getBody(), equalTo("Hello World"));
	}

	@Test
	public void welcomeWithObject() throws Exception {
		ResponseEntity<String> response = template.getForEntity(createURL("/welcome-with-object"), String.class);
		assertThat(response.getBody(), containsString("Hello World"));
	}

	@Test
	public void welcomWithParameter() throws Exception {
		ResponseEntity<String> response = template.getForEntity(createURL("/welcome-with-parameter/name/Buddy"), String.class);
		assertThat(response.getBody(), containsString("Hello World, Buddy"));
	}

}
