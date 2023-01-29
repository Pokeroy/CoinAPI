H2 url：http://localhost:8010/h2-console/
H2 JDBC url：jdbc:h2:mem:todolist

1. 新增
Method：Post
uri：http://localhost:8010/coin/coinData
Post範例：
{
    "currency": "USD",
    "chineseName": "美元"
}

2. 修改
Method：Put
uri：http://localhost:8010/coin/coinData
Put範例：
{
    "currency": "USD",
    "chineseName": "美金"
}

3. 刪除
Method：Delete
uri：http://localhost:8010/coin/coinData/{幣別}

4. 查詢
Method：Get
uri1：http://localhost:8010/coin/coinData/{幣別}
uri2：http://localhost:8010/coin/coinData/

5. coindesk api
Method：Get
uri：http://localhost:8010/coin/coinDeskData

6. New api
Method：Get
uri：http://localhost:8010/coin/currencyInfo