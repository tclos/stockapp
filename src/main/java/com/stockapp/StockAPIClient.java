package com.stockapp;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class StockAPIClient {
    private static final String API_KEY = "API_KEY"; //////// API KEY////////
    private static final String BASE_URL = "https://www.alphavantage.co/query";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public double getCurrentPrice(String symbol) throws IOException, InterruptedException {
        String url = String.format("%s?function=GLOBAL_QUOTE&symbol=%s&apikey=%s",
                BASE_URL,
                symbol,
                API_KEY);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

        if (!json.has("Global Quote")) {
            throw new RuntimeException("Erro com símbolo: " + symbol);
        } else {
            JsonObject quote = json.getAsJsonObject("Global Quote");
            return Double.parseDouble(quote.get("05. price").getAsString());
        }
    }

    public List<HistoricalPrice> getHistoricalPrices(String symbol, LocalDate from, LocalDate to)
            throws IOException, InterruptedException {

        String url = String.format("%s?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s&outputsize=full",
                BASE_URL,
                symbol,
                API_KEY);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

        if (!json.has("Time Series (Daily)")) {
            throw new RuntimeException("Erro ao procurar os precos historicos: " + symbol);
        }

        JsonObject timeSeries = json.getAsJsonObject("Time Series (Daily)");
        List<HistoricalPrice> prices = new ArrayList<>();

        for (String dateStr : timeSeries.keySet()) {
            LocalDate date = LocalDate.parse(dateStr);

            if ((date.isEqual(from) || date.isAfter(from)) && (date.isEqual(to) || date.isBefore(to))) {
                JsonObject dailyData = timeSeries.getAsJsonObject(dateStr);
                double closePrice = Double.parseDouble(dailyData.get("4. close").getAsString());

                prices.add(new HistoricalPrice(date, closePrice));
            }
        }

        // Bubble sort
        for (int i = 0; i < prices.size() - 1; i++) {
            for (int j = 0; j < prices.size() - i - 1; j++) {
                if (prices.get(j).getDate().isAfter(prices.get(j + 1).getDate())) {
                    HistoricalPrice temp = prices.get(j);
                    prices.set(j, prices.get(j + 1));
                    prices.set(j + 1, temp);
                }
            }
        }

        return prices;
    }
}
