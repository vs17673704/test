package com.portal.question;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication


//@EntityScan( basePackages = {"com.portal.question.entity","com.portal.question.cruddemo.entity"} )
public class CruddemoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}
}
