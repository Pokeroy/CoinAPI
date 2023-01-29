package com.example.demo.repo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(name = "coindesk-client", url = "https://api.coindesk.com")
public interface CoinDeskRepo {

    @GetMapping(value = "/v1/bpi/currentprice.json")
    String getCurrentPriceData();

}
