package com.uca.TpBDAvanzadaMarcello;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class TpBdAvanzadaMarcelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(TpBdAvanzadaMarcelloApplication.class, args);
	}

}
