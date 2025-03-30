package com.example.demo.service;

import com.example.demo.entity.Coin;
import com.example.demo.entity.request.CoinReq;
import com.example.demo.repo.CoinRepo;
import com.example.demo.service.intf.ICoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoinService implements ICoinService {

    private final CoinRepo coinRepo;

    @Override
    public Coin findCoinData(String currency) {
        return coinRepo.findByCurrency(currency);
    }

    @Override
    public List<Coin> findAllCoinData() {
        List<Coin> result = new ArrayList<>();
        result.addAll(coinRepo.findAll());
        return result;
    }

    @Override
    @Transactional
    public Coin addCoinData(CoinReq coinReq) {
        Coin addCoin = new Coin();
        addCoin.setCurrency(coinReq.getCurrency());
        addCoin.setChineseName(coinReq.getChineseName());
        return coinRepo.save(addCoin);
    }

    @Override
    @Transactional
    public Coin updateCoinData(CoinReq coinReq) {
        Coin origCoin = coinRepo.findByCurrency(coinReq.getCurrency());
        if (origCoin != null) {
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
    @Transactional
    public void deleteCoinData(String currency) {
        coinRepo.deleteByCurrency(currency);
    }
}
