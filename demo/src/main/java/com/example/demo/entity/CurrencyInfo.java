package com.example.demo.entity;

import java.util.List;

public class CurrencyInfo {
    private String updateTime;
    private List<CoinInfo> coinInfoList;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<CoinInfo> getCoinInfoList() {
        return coinInfoList;
    }

    public void setCoinInfoList(List<CoinInfo> coinInfoList) {
        this.coinInfoList = coinInfoList;
    }

    @Override
    public String toString() {
        return "CurrencyInfo{" +
                "updateTime='" + updateTime + '\'' +
                ", coinInfoList=" + coinInfoList +
                '}';
    }
}
