package com.example.demo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;

@SpringBootApplication
public class DemoApplication {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelmapper=new ModelMapper();
		return modelmapper;
	}
	
	
	

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	
	
	
	

}
