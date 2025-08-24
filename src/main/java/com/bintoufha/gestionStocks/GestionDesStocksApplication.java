package com.bintoufha.gestionStocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GestionDesStocksApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionDesStocksApplication.class, args);
	}

}


