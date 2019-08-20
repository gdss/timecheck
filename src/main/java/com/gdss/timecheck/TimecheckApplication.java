package com.gdss.timecheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class TimecheckApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimecheckApplication.class, args);
	}

}
