package com.haagahelia.cityzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CityzerApplication extends SpringBootServletInitializer {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CityzerApplication.class);
	}


	public static void main(String[] args) throws Exception {
		SpringApplication.run(CityzerApplication.class, args);
	}
}
