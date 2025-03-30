package com.example.demo.controller;

import com.example.demo.entity.Coin;
import com.example.demo.entity.request.CoinReq;
import com.example.demo.entity.response.ApiResponse;
import com.example.demo.service.intf.ICoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coin")
@Tag(name = "幣別管理", description = "幣別相關操作API")
public class CoinController {

    private final ICoinService coinService;

    // 1.查詢 ---------------------------------------------------------
    @GetMapping(value = "/coinData/{currency}")
    @Operation(summary = "查詢特定幣別資料")
    public ResponseEntity<ApiResponse<Coin>> findCoinData(@PathVariable("currency") String currency) {
        Coin result = coinService.findCoinData(currency);
        if (result != null) {
            return ResponseEntity.ok(ApiResponse.success(result));
        } else {
            return ResponseEntity.ok(ApiResponse.success("查無此幣別.", null));
        }
    }

    @GetMapping(value = "/coinData")
    @Operation(summary = "查詢所有幣別資料")
    public ResponseEntity<ApiResponse<List<Coin>>> findAllCoinData() {
        List<Coin> result = coinService.findAllCoinData();
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    // 2.新增 ----------------------------------------------------------
    @PostMapping(value = "/coinData")
    @Operation(summary = "新增幣別資料")
    public ResponseEntity<ApiResponse<Coin>> addCoinData(@Valid @RequestBody CoinReq coinReq) {
        Coin result = coinService.addCoinData(coinReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(result));
    }

    // 3.修改 ----------------------------------------------------------
    @PutMapping(value = "/coinData")
    @Operation(summary = "更新幣別資料")
    public ResponseEntity<ApiResponse<Coin>> updateCoinData(@Valid @RequestBody CoinReq coinReq) {
        Coin result = coinService.updateCoinData(coinReq);
        if (result != null) {
            return ResponseEntity.ok(ApiResponse.success(result));
        } else {
            return ResponseEntity.ok(ApiResponse.success("無此幣別", null));
        }
    }

    // 4.刪除 ----------------------------------------------------------
    @DeleteMapping(value = "/coinData/{currency}")
    @Operation(summary = "刪除幣別資料")
    public ResponseEntity<ApiResponse<Void>> deleteCoinData(@PathVariable String currency) {
        coinService.deleteCoinData(currency);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
