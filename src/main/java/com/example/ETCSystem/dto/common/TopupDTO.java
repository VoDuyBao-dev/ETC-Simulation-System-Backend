package com.example.ETCSystem.dto.common;

import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.entities.Wallet;
import com.example.ETCSystem.enums.TopupMethod;
import com.example.ETCSystem.enums.TopupStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class TopupDTO {
    private Long id;
    private Wallet wallet;
    @JsonIgnore
    private User user;

    private BigDecimal amount;


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
}
