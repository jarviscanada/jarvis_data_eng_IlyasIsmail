package com.jrvs.trading;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class TradingApplication implements CommandLineRunner  {

	public static void main(String[] args) {
		SpringApplication.run(TradingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Running");
	}
}
