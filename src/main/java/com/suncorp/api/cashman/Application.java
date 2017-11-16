package com.suncorp.api.cashman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Main entry point for cashman service.
 *
 * @author Haneesa Sulfikhar (haneesa)
 */
@ComponentScan(basePackages = "com.suncorp.api.cashman")
@Import(JerseyConfiguration.class)
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
