package com.example.demo.service.intf;

import com.example.demo.entity.Coin;
import com.example.demo.entity.request.CoinReq;

import java.util.List;

public interface ICoinService {

    // 1.查詢 ---------------------------------------------------------
    Coin findCoinData(String currency);

    List<Coin> findAllCoinData();

    // 2.新增 ---------------------------------------------------------
    Coin addCoinData(CoinReq coinReq);

    // 3.修改 ---------------------------------------------------------
    Coin updateCoinData(CoinReq coinReq);

    // 4.刪除 ---------------------------------------------------------
    void deleteCoinData(String currency);

}
