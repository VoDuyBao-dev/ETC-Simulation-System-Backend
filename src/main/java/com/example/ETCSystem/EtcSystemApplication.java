package com.example.ETCSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EtcSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtcSystemApplication.class, args);
	}

}
