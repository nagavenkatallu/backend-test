package com.backend.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
	@GetMapping("/api/hello")
	public String sayHello() {
		return "Ila links inkosari open cheyyaku \uD83D\uDE1B";
	}
	@GetMapping("/")
	public  String homePage(){return "Home page";}

}
