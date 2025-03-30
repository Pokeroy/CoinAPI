package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.entity.request.CoinReq;
import com.example.demo.lib.JsonUtil;
import com.example.demo.repo.CoinDeskRepo;
import com.example.demo.repo.CoinRepo;
import com.example.demo.service.intf.ICoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoinService implements ICoinService {

    private SimpleDateFormat sdf;

    private final CoinDeskRepo coinDeskRepo;
    private final CoinRepo coinRepo;

    @Override
    public Coin findCoinData(String currency) {
        log.info("查詢幣別資料: {}", currency);
        Coin result = coinRepo.findByCurrency(currency);
        log.info("幣別 {} 查詢結果: {}", currency, result != null ? "成功" : "未找到");
        return result;
    }

    @Override
    public List<Coin> findAllCoinData() {
        log.info("查詢所有幣別資料");
        List<Coin> result = new ArrayList<>();
        coinRepo.findAll().forEach(result::add);
        log.info("查詢所有幣別資料結果: 共 {} 筆資料", result.size());
        return result;
    }

    @Override
    public Coin addCoinData(CoinReq coinReq) {
        log.info("新增幣別資料: {}", coinReq);
        Coin addCoin = new Coin();
        addCoin.setCurrency(coinReq.getCurrency());
        addCoin.setChineseName(coinReq.getChineseName());
        Coin savedCoin = coinRepo.save(addCoin);
        log.info("幣別 {} 新增成功, ID: {}", savedCoin.getCurrency(), savedCoin.getId());
        return savedCoin;
    }

    @Override
    public Coin updateCoinData(CoinReq coinReq) {
        log.info("更新幣別資料: {}", coinReq);
        Coin origCoin = coinRepo.findByCurrency(coinReq.getCurrency());
        if (origCoin != null) {
            log.info("找到原有幣別資料: ID={}, 中文名稱={}", origCoin.getId(), origCoin.getChineseName());
            Coin updateCoin = new Coin();
            updateCoin.setId(origCoin.getId());
            updateCoin.setCurrency(coinReq.getCurrency());
            updateCoin.setChineseName(coinReq.getChineseName());
            Coin savedCoin = coinRepo.save(updateCoin);
            log.info("幣別 {} 更新成功", savedCoin.getCurrency());
            return savedCoin;
        }

        log.warn("更新幣別 {} 失敗: 找不到原有資料", coinReq.getCurrency());
        return null;
    }

    @Override
    public void deleteCoinData(String currency) {
        log.info("刪除幣別資料: {}", currency);
        coinRepo.deleteByCurrency(currency);
        log.info("幣別 {} 刪除成功", currency);
    }

    @Override
    public String getCoinDeskData() {
        log.info("取得 CoinDesk API 資料");
        String data = coinDeskRepo.getCurrentPriceData();
        log.info("成功取得 CoinDesk API 資料, 長度: {}", data != null ? data.length() : 0);
        return data;
    }

    @Override
    public CurrencyInfo parseCurrencyInfo(String coinDeskData) {
        log.info("解析 CoinDesk API 資料");
        CurrentPrice currentPrice = JsonUtil.toObject(coinDeskData, CurrentPrice.class);
        CurrencyInfo currencyInfo = new CurrencyInfo();
        currencyInfo.setUpdateTime(parseTime(currentPrice.getTime().getUpdatedISO()));
        currencyInfo.setCoinInfoList(parseCoinList(currentPrice));
        log.info("CoinDesk API 資料解析完成, 更新時間: {}, 幣別數量: {}", 
            currencyInfo.getUpdateTime(), 
            currencyInfo.getCoinInfoList() != null ? currencyInfo.getCoinInfoList().size() : 0);
        return currencyInfo;
    }

    private String parseTime(Date time) {
        try {
            if (sdf == null) {
                sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            }
            return sdf.format(time);
        } catch (Exception e) {
            log.error("解析時間出錯: {}", time, e);
            return null;
        }
    }

    private List<CoinInfo> parseCoinList(CurrentPrice currentPrice) {
        log.info("開始解析幣別列表");
        List<CoinInfo> coinList = new ArrayList<>();

        List<Coin> coins = findAllCoinData();

        // USD
        USD usd = currentPrice.getBpi().getUsd();
        CoinInfo usdCoin = new CoinInfo();
        usdCoin.setCurrency(usd.getCode());
        usdCoin.setCurrencyChineseName(parseChineseName(coins, usdCoin.getCurrency()));
        usdCoin.setExchangeRate(usd.getRate());
        coinList.add(usdCoin);
        log.info("解析 USD 幣別: 匯率={}, 中文名稱={}", usd.getRate(), usdCoin.getCurrencyChineseName());

        // GBP
        GBP gbp = currentPrice.getBpi().getGbp();
        CoinInfo gbpCoin = new CoinInfo();
        gbpCoin.setCurrency(gbp.getCode());
        gbpCoin.setCurrencyChineseName(parseChineseName(coins, gbpCoin.getCurrency()));
        gbpCoin.setExchangeRate(gbp.getRate());
        coinList.add(gbpCoin);
        log.info("解析 GBP 幣別: 匯率={}, 中文名稱={}", gbp.getRate(), gbpCoin.getCurrencyChineseName());

        // EUR
        EUR eur = currentPrice.getBpi().getEur();
        CoinInfo eurCoin = new CoinInfo();
        eurCoin.setCurrency(eur.getCode());
        eurCoin.setCurrencyChineseName(parseChineseName(coins, eurCoin.getCurrency()));
        eurCoin.setExchangeRate(eur.getRate());
        coinList.add(eurCoin);
        log.info("解析 EUR 幣別: 匯率={}, 中文名稱={}", eur.getRate(), eurCoin.getCurrencyChineseName());

        log.info("幣別列表解析完成, 共 {} 種幣別", coinList.size());
        return coinList;
    }

    private String parseChineseName(List<Coin> coins, String currency) {
        if (coins == null || coins.isEmpty()) {
            log.warn("找不到任何幣別資料");
            return null;
        }

        Coin coin = coins.stream()
                .filter(x -> x.getChineseName() != null)
                .filter(x -> x.getCurrency().equals(currency))
                .findFirst().orElse(null);

        if (coin != null) {
            log.debug("找到幣別 {} 的中文名稱: {}", currency, coin.getChineseName());
            return coin.getChineseName();
        } else {
            log.warn("找不到幣別 {} 的中文名稱", currency);
            return null;
        }
    }
}
