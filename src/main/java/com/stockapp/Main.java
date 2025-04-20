package com.stockapp;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        StockAPIClient api = new StockAPIClient();
        
        List<HistoricalPrice> prices = api.getHistoricalPrices(
            "AAPL",
            LocalDate.of(2023, 1, 1),
            LocalDate.of(2023, 2, 1)
        );

        for (HistoricalPrice price : prices) {
            System.out.println(price);
        }
    }
}
