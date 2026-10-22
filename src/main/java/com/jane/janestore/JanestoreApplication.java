package com.jane.janestore;

import com.jane.janestore.dto.ContactInfoDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@EnableConfigurationProperties(ContactInfoDto.class)
public class JanestoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(JanestoreApplication.class, args);
	}

}
