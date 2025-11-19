package com.example.ETCSystem.dto.common;

import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.entities.Wallet;
import com.example.ETCSystem.enums.TopupMethod;
import com.example.ETCSystem.enums.TopupStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopupDTO {
    private Long id;
    private Wallet wallet;
    @JsonIgnore
    private User user;

    private BigDecimal amount;

    private BigDecimal balanceAfter;

    private TopupMethod method; // BANK, VNPAY

    private String referenceCode;

    private String transactionNo;
    private String bankCode;
    private LocalDateTime payDate;
    private String vnpResponseCode;
    private String description;
    private TopupStatus status; // PENDING, COMPLETED, FAILED
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

//    dùng cho trả về lịch sử nạp tiền user. tạo các trường cơ bản
    public TopupDTO(Long id, BigDecimal amount, BigDecimal balanceAfter ,TopupMethod method, LocalDateTime createdAt) {
        this.id = id;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.method = method;
        this.createdAt = createdAt;
    }
}
