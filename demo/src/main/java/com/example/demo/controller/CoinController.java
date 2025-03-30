package com.example.demo.controller;

import com.example.demo.entity.Coin;
import com.example.demo.entity.CurrencyInfo;
import com.example.demo.entity.request.CoinReq;
import com.example.demo.service.intf.ICoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CoinController {

    private final ICoinService coinService;

    // 1.查詢 ---------------------------------------------------------
    @GetMapping(value = "/coin/coinData/{currency}")
    public Coin findCoinData(@PathVariable("currency") String currency) {
        return coinService.findCoinData(currency);
    }

    @GetMapping(value = "/coin/coinData")
    public List<Coin> findAllCoinData() {
        return coinService.findAllCoinData();
    }

    // 2.新增 ----------------------------------------------------------
    @PostMapping(value = "/coin/coinData")
    public Coin addCoinData(@RequestBody CoinReq coinReq) {
        return coinService.addCoinData(coinReq);
    }

    // 3.修改 ----------------------------------------------------------
    @PutMapping(value = "/coin/coinData")
    public Coin updateCoinData(@RequestBody CoinReq coinReq) {
        return coinService.updateCoinData(coinReq);
    }

    // 4.刪除 ----------------------------------------------------------
    @DeleteMapping(value = "/coin/coinData/{currency}")
    public void deleteCoinData(@PathVariable String currency) {
        coinService.deleteCoinData(currency);
    }

    // 5.取得 CoinDesk API 資料 -----------------------------------------
    @GetMapping(value = "/coin/coinDeskData")
    public String getCoinDeskData() {
        return coinService.getCoinDeskData();
    }

    // 6.取得幣別資訊 ----------------------------------------------------
    @GetMapping(value = "/coin/currencyInfo")
    public CurrencyInfo getCurrencyInfo() {
        return coinService.parseCurrencyInfo(coinService.getCoinDeskData());
    }

}
