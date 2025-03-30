package com.example.demo;

import com.example.demo.entity.Coin;
import com.example.demo.entity.request.CoinReq;
import com.example.demo.service.intf.ICoinService;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoinServiceTests {

    @Autowired
    private ICoinService coinService;
    
    @AfterEach
    void cleanup() {
        // 每次測試後清理資料庫，確保測試之間不互相影響
        List<Coin> coins = coinService.findAllCoinData();
        for (Coin coin : coins) {
            coinService.deleteCoinData(coin.getCurrency());
        }
    }

    @Test
    @DisplayName("測試查詢單一幣別資料")
    void shouldFindCoinDataById() {
        // 新增
        Coin savedCoin = coinService.addCoinData(
            CoinReq.builder()
                .currency("GBP")
                .chineseName("英鎊")
                .build()
        );

        // 查詢
        Coin foundCoin = coinService.findCoinData("GBP");
        
        // 斷言
        assertNotNull(foundCoin, "查詢到的幣別不應為空");
        assertEquals(savedCoin.getId(), foundCoin.getId(), "ID應相同");
        assertEquals("GBP", foundCoin.getCurrency(), "幣別代碼應為GBP");
        assertEquals("英鎊", foundCoin.getChineseName(), "中文名稱應為英鎊");
    }

    @Test
    @DisplayName("測試查詢所有幣別資料")
    void shouldFindAllCoinData() {
        // 新增
        coinService.addCoinData(
            CoinReq.builder()
                .currency("EUR")
                .chineseName("歐元")
                .build()
        );
        
        coinService.addCoinData(
            CoinReq.builder()
                .currency("JPY")
                .chineseName("日圓")
                .build()
        );

        // 查詢
        List<Coin> coins = coinService.findAllCoinData();
        
        // 斷言
        assertNotNull(coins, "查詢結果不應為空");
        assertEquals(2, coins.size(), "應該有兩筆幣別資料");
        assertTrue(coins.stream().anyMatch(c -> "EUR".equals(c.getCurrency())), "結果中應包含歐元");
        assertTrue(coins.stream().anyMatch(c -> "JPY".equals(c.getCurrency())), "結果中應包含日圓");
    }

    @Test
    @DisplayName("測試新增幣別資料")
    void shouldAddCoinData() {
        // 新增
        Coin savedCoin = coinService.addCoinData(
            CoinReq.builder()
                .currency("TWD")
                .chineseName("新台幣")
                .build()
        );
        
        // 斷言
        assertNotNull(savedCoin, "新增的幣別不應為空");
        assertNotNull(savedCoin.getId(), "ID不應為空");
        assertEquals("TWD", savedCoin.getCurrency(), "幣別代碼應為TWD");
        assertEquals("新台幣", savedCoin.getChineseName(), "中文名稱應為新台幣");
        
        // 驗證是否真的保存到了資料庫
        Coin foundCoin = coinService.findCoinData("TWD");
        assertNotNull(foundCoin, "應該能從資料庫中查詢到新增的幣別");
        assertEquals(savedCoin.getId(), foundCoin.getId(), "查詢到的ID應與新增時相同");
    }

    @Test
    @DisplayName("測試更新幣別資料")
    void shouldUpdateCoinData() {
        // 新增
        Coin originalCoin = coinService.addCoinData(
            CoinReq.builder()
                .currency("USD")
                .chineseName("美元")
                .build()
        );

        // 更新
        Coin updatedCoin = coinService.updateCoinData(
            CoinReq.builder()
                .currency("USD")
                .chineseName("美金")
                .build()
        );
        
        // 斷言
        assertNotNull(updatedCoin, "更新後的幣別不應為空");
        assertEquals(originalCoin.getId(), updatedCoin.getId(), "ID應保持不變");
        assertEquals("USD", updatedCoin.getCurrency(), "幣別代碼應為USD");
        assertEquals("美金", updatedCoin.getChineseName(), "中文名稱應更新為美金");
        
        // 驗證資料庫中的資料是否已更新
        Coin foundCoin = coinService.findCoinData("USD");
        assertEquals("美金", foundCoin.getChineseName(), "資料庫中的資料應該已更新");
    }

    @Test
    @DisplayName("測試刪除幣別資料")
    void shouldDeleteCoinData() {
        // 新增
        coinService.addCoinData(
            CoinReq.builder()
                .currency("USD")
                .chineseName("美元")
                .build()
        );
        
        // 確認資料已存在
        assertNotNull(coinService.findCoinData("USD"), "刪除前應能查詢到資料");

        // 刪除
        coinService.deleteCoinData("USD");
        
        // 斷言
        List<Coin> coins = coinService.findAllCoinData();
        assertTrue(coins.stream().noneMatch(c -> "USD".equals(c.getCurrency())), "刪除後應查不到該筆資料");
    }
}
