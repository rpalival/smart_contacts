package com.rpalival.smart_contacts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("application-${env}.properties")
public class SmartContactsApplication {
	public static void main(String[] args) {
		SpringApplication.run(SmartContactsApplication.class, args);
	}

}