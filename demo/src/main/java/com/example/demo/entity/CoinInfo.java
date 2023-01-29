package com.example.demo.entity;

public class CoinInfo {
    private String currency;
    private String currencyChineseName;
    private String exchangeRate;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyChineseName() {
        return currencyChineseName;
    }

    public void setCurrencyChineseName(String currencyChineseName) {
        this.currencyChineseName = currencyChineseName;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return "CoinInfo{" +
                "currency='" + currency + '\'' +
                ", currencyChineseName='" + currencyChineseName + '\'' +
                ", exchangeRate='" + exchangeRate + '\'' +
                '}';
    }
}
