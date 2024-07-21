package com.BloodBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.BloodBank")
public class BloodBankProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(BloodBankProjectApplication.class, args);
	}
}
