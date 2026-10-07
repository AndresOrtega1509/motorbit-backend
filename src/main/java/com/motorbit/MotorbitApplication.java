package com.motorbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan 
public class MotorbitApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotorbitApplication.class, args);
	}

}
