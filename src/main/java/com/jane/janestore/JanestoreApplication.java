package com.jane.janestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class JanestoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(JanestoreApplication.class, args);
	}

}
