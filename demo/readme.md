# 幣別資訊管理 API

## 專案簡介

這是一個基於 Spring Boot 建構的 RESTful API 服務，專門用於管理各種幣別資訊。本專案實現了完整的 CRUD 操作功能，提供標準化的幣別資料管理介面，並包含完整的參數驗證和錯誤處理機制。

## 核心功能

- **幣別資料完整管理**：支援幣別資訊的新增、查詢、更新與刪除
- **標準化數據格式**：採用統一的 API 響應格式
- **完整的參數驗證**：包含幣別代碼和中文名稱的格式驗證
- **全面的錯誤處理**：統一的例外處理機制

## 技術棧

- **核心框架**：Spring Boot 2.x
- **持久層框架**：Spring Data JPA
- **API文檔**：SpringDoc OpenAPI (Swagger 3)
- **開發工具與套件**：
  - Lombok：簡化 Java 代碼
  - Validation：參數驗證
  - SLF4J：日誌記錄
- **資料庫**：H2 Database (可配置其他資料庫)

## 系統架構設計

採用標準三層架構：

- **Controller 層**：
  - REST API 端點定義
  - 請求參數驗證
  - 統一響應封裝
- **Service 層**：
  - 業務邏輯處理
  - 事務管理
  - 日誌記錄
- **Repository 層**：
  - 數據訪問操作
  - JPA 命名查詢

## API 端點規格

| 端點路徑 | HTTP 方法 | 功能說明 |
|---------|----------|---------|
| `/coin/coinData/{currency}` | GET | 查詢指定幣別的詳細資訊 |
| `/coin/coinData` | GET | 查詢所有幣別列表 |
| `/coin/coinData` | POST | 新增幣別資訊 |
| `/coin/coinData` | PUT | 更新幣別資訊 |
| `/coin/coinData/{currency}` | DELETE | 刪除指定幣別 |

### 數據模型

#### 請求對象 (CoinReq)

```json
{
  "currency": "USD",     // 幣別代碼（2-5個大寫英文字母）
  "chineseName": "美元"  // 中文名稱（最多20字）
}
```

#### 響應格式 (ApiResponse)

```json
{
  "success": true,          // 操作是否成功
  "message": "操作成功",    // 響應消息
  "data": {                 // 響應數據
    "id": 1,
    "currency": "USD",
    "chineseName": "美元"
  }
}
```

### 參數驗證規則

- **幣別代碼 (currency)**
  - 不能為空
  - 長度必須在2-5個字符之間
  - 必須是大寫英文字母
- **中文名稱 (chineseName)**
  - 不能為空
  - 最大長度20個字符

## 錯誤處理

系統實現了統一的錯誤處理機制，主要包含：

- 資源不存在異常 (ResourceNotFoundException)
- 參數驗證異常 (MethodArgumentNotValidException)
- 約束違反異常 (ConstraintViolationException)
- 通用業務異常 (IllegalArgumentException)

所有異常都會被轉換為標準的 API 響應格式。

## 快速開始

### 環境要求

- JDK 8 或更高版本
- Maven 3.x
- Git (可選)

### 部署步驟

1. 克隆專案
   ```bash
   git clone [repository-url]
   ```

2. 進入專案目錄
   ```bash
   cd coin-api
   ```

3. 編譯打包
   ```bash
   mvn clean package
   ```

4. 運行應用
   ```bash
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

### API 文檔

啟動應用後，可通過以下地址訪問 Swagger 文檔：
```
http://localhost:8080/swagger-ui.html
```

## 開發建議

1. 遵循 RESTful API 設計規範
2. 確保所有新增的 API 都有適當的參數驗證
3. 保持統一的響應格式
4. 添加完整的日誌記錄
5. 使用事務管理確保數據一致性
