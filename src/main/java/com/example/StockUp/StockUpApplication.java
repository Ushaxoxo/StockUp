package com.example.StockUp;

import com.example.StockUp.object.Stock;
import com.example.StockUp.service.StockService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition
public class StockUpApplication {

	private static final Logger logger = LoggerFactory.getLogger(StockUpApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StockUpApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StockService stockService) {
		return args -> {
			try {
				stockService.saveStockDetails(createStock("AAPL", "Apple Inc."));
				stockService.saveStockDetails(createStock("GOOGL", "Alphabet Inc."));
				logger.info("Initial stocks saved successfully.");
			} catch (Exception e) {
				logger.error("Failed to save initial stocks", e);
			}
		};
	}

	private Stock createStock(String symbol, String companyName) {
		Stock stock = new Stock();
		stock.setSymbol(symbol);
		stock.setCompanyName(companyName);
		stock.setCurrentPrice(100.0f); // Example initial price
		stock.setOpeningPrice(100.0f);
		stock.setClosingPrice(100.0f);
		return stock;
	}
}
