package com.foodsafety;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FoodsafetyApplication {
	/* access http://localhost:5005/swagger-ui/swagger-ui/index.html to see documentation */

	public static void main(String[] args) {
		SpringApplication.run(FoodsafetyApplication.class, args);
	}

}
