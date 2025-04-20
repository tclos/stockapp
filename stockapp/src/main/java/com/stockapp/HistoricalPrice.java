package com.stockapp;

import java.time.LocalDate;

public class HistoricalPrice {
    private LocalDate date;
    private double closePrice;

    public HistoricalPrice(LocalDate date, double closePrice) {
        this.date = date;
        this.closePrice = closePrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getClosePrice() {
        return closePrice;
    }

    @Override
    public String toString() {
        return date + ": $" + closePrice;
    }
}
