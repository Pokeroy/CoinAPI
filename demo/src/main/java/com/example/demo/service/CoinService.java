package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.entity.request.CoinReq;
import com.example.demo.lib.JsonUtil;
import com.example.demo.repo.CoinDeskRepo;
import com.example.demo.repo.CoinRepo;
import com.example.demo.service.intf.ICoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CoinService implements ICoinService {

    private static final Logger logger = LoggerFactory.getLogger(CoinService.class);

    private SimpleDateFormat sdf;

    @Autowired
    private CoinDeskRepo coinDeskRepo;
    @Autowired
    private CoinRepo coinRepo;

    @Override
    public Coin findCoinData(String currency) {
        return coinRepo.findByCurrency(currency);
    }

    @Override
    public List<Coin> findAllCoinData() {
        List<Coin> result = new ArrayList<>();
        coinRepo.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Coin addCoinData(CoinReq coinReq) {
        Coin addCoin = new Coin();
        addCoin.setCurrency(coinReq.getCurrency());
        addCoin.setChineseName(coinReq.getChineseName());
        return coinRepo.save(addCoin);
    }

    @Override
    public Coin updateCoinData(CoinReq coinReq) {
        Coin origCoin = coinRepo.findByCurrency(coinReq.getCurrency());
        if (origCoin != null) {
            Coin updateCoin = new Coin();
            updateCoin.setId(origCoin.getId());
            updateCoin.setCurrency(coinReq.getCurrency());
            updateCoin.setChineseName(coinReq.getChineseName());
            return coinRepo.save(updateCoin);
        }

        return null;
    }

    @Override
    public void deleteCoinData(String currency) {
        coinRepo.deleteByCurrency(currency);
    }

    @Override
    public String getCoinDeskData() {
        return coinDeskRepo.getCurrentPriceData();
    }

    @Override
    public CurrencyInfo parseCurrencyInfo(String coinDeskData) {
        CurrentPrice currentPrice = JsonUtil.toObject(coinDeskData, CurrentPrice.class);
        CurrencyInfo currencyInfo = new CurrencyInfo();
        currencyInfo.setUpdateTime(parseTime(currentPrice.getTime().getUpdatedISO()));
        currencyInfo.setCoinInfoList(parseCoinList(currentPrice));
        return currencyInfo;
    }

    private String parseTime(Date time) {
        try {
            if (sdf == null) {
                sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            }
            return sdf.format(time);
        } catch (Exception e) {
            logger.error("Occur error during parseTime: {}", time, e);
            return null;
        }
    }

    private List<CoinInfo> parseCoinList(CurrentPrice currentPrice) {
        List<CoinInfo> coinList = new ArrayList<>();

        List<Coin> coins = findAllCoinData();

        // USD
        USD usd = currentPrice.getBpi().getUsd();
        CoinInfo usdCoin = new CoinInfo();
        usdCoin.setCurrency(usd.getCode());
        usdCoin.setCurrencyChineseName(parseChineseName(coins, usdCoin.getCurrency()));
        usdCoin.setExchangeRate(usd.getRate());
        coinList.add(usdCoin);

        // GBP
        GBP gbp = currentPrice.getBpi().getGbp();
        CoinInfo gbpCoin = new CoinInfo();
        gbpCoin.setCurrency(gbp.getCode());
        gbpCoin.setCurrencyChineseName(parseChineseName(coins, gbpCoin.getCurrency()));
        gbpCoin.setExchangeRate(gbp.getRate());
        coinList.add(gbpCoin);

        // EUR
        EUR eur = currentPrice.getBpi().getEur();
        CoinInfo eurCoin = new CoinInfo();
        eurCoin.setCurrency(eur.getCode());
        eurCoin.setCurrencyChineseName(parseChineseName(coins, eurCoin.getCurrency()));
        eurCoin.setExchangeRate(eur.getRate());
        coinList.add(eurCoin);

        return coinList;
    }

    private String parseChineseName(List<Coin> coins, String currency) {
        if (coins == null || coins.isEmpty()) {
            return null;
        }

        Coin coin = coins.stream()
                .filter(x -> x.getChineseName() != null)
                .filter(x -> x.getCurrency().equals(currency))
                .findFirst().orElse(null);

        if (coin != null) {
            return coin.getChineseName();
        } else {
            return null;
        }
    }

}
