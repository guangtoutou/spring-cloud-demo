package com.nilab.quoteservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
@RequestMapping("quote")
public class QuoteServiceApplication {
	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(QuoteServiceApplication.class, args);
	}

	@RequestMapping("/{userid}")
	public String getPrice(@PathVariable("userid") String userid){
		ResponseEntity res = restTemplate
				.exchange("http://user-service:8002/user/"+userid, HttpMethod.GET,null, String.class);
		return "user " + res.getBody().toString().toUpperCase() + " are holding stocks as below:";
	}
}
