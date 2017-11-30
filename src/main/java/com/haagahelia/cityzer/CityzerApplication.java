package com.haagahelia.cityzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.haagahelia.cityzer"})
public class CityzerApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(CityzerApplication.class, args);
	}
}