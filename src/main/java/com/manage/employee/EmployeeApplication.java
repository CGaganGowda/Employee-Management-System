package com.manage.employee;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Employee Management System REST API",
				description = "EMS Rest Services",
				version = "v1.0",
				contact = @Contact(
						name = "Gagan",
						email = "gagancgowda971@gmail.com"
				),
				license = @License(
						name = "Apache 1.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "EMS Documentation",
				url = "#"
		)
)
public class EmployeeApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}


}
