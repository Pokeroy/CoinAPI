package com.example.demo;

import com.example.demo.entity.Coin;
import com.example.demo.entity.CurrencyInfo;
import com.example.demo.entity.request.CoinReq;
import com.example.demo.service.intf.ICoinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class CoinServiceTests {

    @Autowired
    private ICoinService coinService;

    @Test
    void findCoinDataTest() {
        // 新增
        CoinReq usaCoinReq = new CoinReq();
        usaCoinReq.setCurrency("GBP");
        usaCoinReq.setChineseName("英鎊");
        Coin usaCoin = coinService.addCoinData(usaCoinReq);

        // 查詢
        Coin queryCoin = coinService.findCoinData("GBP");
        System.out.println("queryCoin = " + queryCoin);
        Assert.isTrue(usaCoin.getId() == queryCoin.getId());
        Assert.isTrue(usaCoin.getCurrency().equals(queryCoin.getCurrency()));
        Assert.isTrue(usaCoin.getChineseName().equals(queryCoin.getChineseName()));
    }

    @Test
    void findAllCoinDataTest() {
        // 新增
        CoinReq usaCoinReq = new CoinReq();
        usaCoinReq.setCurrency("EUP");
        usaCoinReq.setChineseName("歐元");
        Coin usaCoin = coinService.addCoinData(usaCoinReq);

        // 查詢
        List<Coin> coins = coinService.findAllCoinData();
        System.out.println("coins = " + coins);
        Assert.isTrue(!coins.isEmpty());
    }

    @Test
    void addCoinDataTest() {
        CoinReq usaCoinReq = new CoinReq();
        usaCoinReq.setCurrency("TW");
        usaCoinReq.setChineseName("台幣");

        Coin usaCoin = coinService.addCoinData(usaCoinReq);
        System.out.println("usaCoin = " + usaCoin);
        Assert.notNull(usaCoin);
    }

    @Test
    void updateCoinDataTest() {
        // 新增
        CoinReq usaCoinReq = new CoinReq();
        usaCoinReq.setCurrency("USD");
        usaCoinReq.setChineseName("美元");
        Coin usaCoin = coinService.addCoinData(usaCoinReq);

        //更新
        CoinReq updateCoinReq = new CoinReq();
        updateCoinReq.setCurrency("USD");
        updateCoinReq.setChineseName("美金");
        Coin updateCoin = coinService.updateCoinData(updateCoinReq);

        Assert.isTrue(usaCoin.getId() == updateCoin.getId());
        Assert.isTrue(usaCoin.getCurrency().equals(updateCoin.getCurrency()));
        Assert.isTrue(!usaCoin.getChineseName().equals(updateCoin.getChineseName()));
    }

    @Test
    void deleteCoinDataTest() {
        // 新增
        CoinReq usaCoinReq = new CoinReq();
        usaCoinReq.setCurrency("USD");
        usaCoinReq.setChineseName("美元");
        coinService.addCoinData(usaCoinReq);

        //刪除
        coinService.deleteCoinData("USD");

        //查詢
        List<Coin> coins = coinService.findAllCoinData();
        Assert.isTrue(coins.isEmpty());
    }

    @Test
    void getCoinDeskDataTest() {
        String coinDeskDara = coinService.getCoinDeskData();
        System.out.println("coinDeskDara = " + coinDeskDara);
        Assert.notNull(coinDeskDara);
    }

    @Test
    void getCurrencyInfoTest() {
        CurrencyInfo currencyInfo =
                coinService.parseCurrencyInfo(coinService.getCoinDeskData());
        System.out.println("currencyInfo = " + currencyInfo);
        Assert.notNull(currencyInfo);
    }

}
