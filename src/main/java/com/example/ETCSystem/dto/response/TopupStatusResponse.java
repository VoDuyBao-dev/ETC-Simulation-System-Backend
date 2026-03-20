package com.example.ETCSystem.dto.response;

import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.entities.Wallet;
import com.example.ETCSystem.enums.TopupMethod;
import com.example.ETCSystem.enums.TopupStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopupStatusResponse {
    private Long id;
    private BigDecimal amount;
    private TopupStatus status;
//    private BigDecimal balanceAfter;
//    private TopupMethod method; // BANK, VNPAY
//    private String referenceCode;
//    private String transactionNo;
//    private String bankCode;
//    private LocalDateTime payDate;
//
//    private LocalDateTime createdAt;
//    private LocalDateTime completedAt;

}
