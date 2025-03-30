package com.example.demo.service;

import com.example.demo.entity.Coin;
import com.example.demo.entity.request.CoinReq;
import com.example.demo.repo.CoinRepo;
import com.example.demo.service.intf.ICoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoinService implements ICoinService {

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
}
