package com.bmbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class BmBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BmBatchApplication.class, args);
	}

}
