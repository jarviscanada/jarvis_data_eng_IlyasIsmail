package com.jrvs.trading;

import com.jrvs.trading.account.Account;
import com.jrvs.trading.quote.Quote;
import com.jrvs.trading.quote.QuoteService;
import com.jrvs.trading.traderAccount.Trader;
import com.jrvs.trading.traderAccount.TraderAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class TradingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TradingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Running");
	}
}
