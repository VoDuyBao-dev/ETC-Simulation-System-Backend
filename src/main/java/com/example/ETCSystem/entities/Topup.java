package com.example.ETCSystem.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.example.ETCSystem.enums.TopupMethod;
import com.example.ETCSystem.enums.TopupStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "topups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TopupMethod method; // BANK, VNPAY

    private String referenceCode;

    private String transactionNo;
    private String bankCode;
    private LocalDateTime payDate;
    private String vnpResponseCode;


    private String description;

    @Enumerated(EnumType.STRING)
    private TopupStatus status; // PENDING, COMPLETED, FAILED

    @Column(name = "balance_after")
    private BigDecimal balanceAfter;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime completedAt;
}
