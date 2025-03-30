package com.example.demo.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bpi {
    @JsonProperty("USD")
    private USD usd;
    @JsonProperty("GBP")
    private GBP gbp;
    @JsonProperty("EUR")
    private EUR eur;
}
