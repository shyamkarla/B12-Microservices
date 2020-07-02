package com.b12.offer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OfferApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfferApplication.class, args);
	}

}
