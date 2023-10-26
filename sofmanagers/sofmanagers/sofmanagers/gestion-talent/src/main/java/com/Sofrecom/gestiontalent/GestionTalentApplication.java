package com.Sofrecom.gestiontalent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GestionTalentApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionTalentApplication.class, args);
	}

}
