package com.jrvs.trading;

import com.jrvs.trading.marketData.MarketDataConfig;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"ca.jrvs.trading.dao", "ca.jrvs.trading.service"})
public class TestConfig {

    @Bean
    public MarketDataConfig marketDataConfig() {
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("APILINK");
        marketDataConfig.setToken("TOKEN");
        return marketDataConfig;
    }

    @Bean
    public DataSource dataSource() {
        System.out.println("Creating data source");
        String url = "jdbc:postgresql://localhost:5432/jrvstrading_test";
        String user = "postgres";
        String password = "password";
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }
}
