package xyz.hyunto.rdbmsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class RdbmsServerApplication {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
		String clientSecret = "123456";
		clientSecret = encoder.encode(clientSecret);

		System.out.println("============================================================");
		System.out.println(clientSecret);
		System.out.println("============================================================");

		SpringApplication.run(RdbmsServerApplication.class, args);
	}

}

