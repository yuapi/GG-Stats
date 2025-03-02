package com.yuapi.GameStatWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableJpaAuditing
@SpringBootApplication
public class GameStatWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameStatWebApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() { return new RestTemplate(); }

}
