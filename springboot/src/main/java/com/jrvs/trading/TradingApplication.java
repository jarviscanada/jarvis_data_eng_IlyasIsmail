package com.jrvs.trading;

import com.jrvs.trading.marketData.Quote;
import com.jrvs.trading.marketData.QuoteDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@RestController
public class TradingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingApplication.class, args);
	}

	@GetMapping
	public String hello() {
		return "hello world";
	}

//	@Bean
//	CommandLineRunner commandLineRunner(QuoteDao quoteDao) {
//		return args -> {
//			String date = "2024-02-12";
//			Date fixedDate = new SimpleDateFormat("2024-02-12").parse(date);
//			Quote quote = new Quote(
//					"MSFTTT", 100, 100, 100, 100, 1010, fixedDate, 100, 100, "100%");
//			quoteDao.save(quote);
//		};
//	}

}
