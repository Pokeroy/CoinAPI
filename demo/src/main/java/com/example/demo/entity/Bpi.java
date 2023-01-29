package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bpi {
    @JsonProperty("USD")
    private USD usd;
    @JsonProperty("GBP")
    private GBP gbp;
    @JsonProperty("EUR")
    private EUR eur;

    public USD getUsd() {
        return usd;
    }

    public void setUsd(USD usd) {
        this.usd = usd;
    }

    public GBP getGbp() {
        return gbp;
    }

    public void setGbp(GBP gbp) {
        this.gbp = gbp;
    }

    public EUR getEur() {
        return eur;
    }

    public void setEur(EUR eur) {
        this.eur = eur;
    }

    @Override
    public String toString() {
        return "Bpi{" +
                "usd=" + usd +
                ", gbp=" + gbp +
                ", eur=" + eur +
                '}';
    }
}
