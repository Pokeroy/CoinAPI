package com.example.demo.entity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinReq {
    @NotBlank(message = "幣別代碼不能為空")
    @Size(min = 2, max = 5, message = "幣別代碼長度必須在2-5之間")
    @Pattern(regexp = "^[A-Z]{2,5}$", message = "幣別代碼必須是2-5個大寫英文字母")
    private String currency;
    
    @NotBlank(message = "中文名稱不能為空")
    @Size(max = 20, message = "中文名稱最長不超過20個字符")
    private String chineseName;
}
