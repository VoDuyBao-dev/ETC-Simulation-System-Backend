package com.example.ETCSystem.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.example.ETCSystem.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallet_transactions")
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "related_toll_transaction_id")
    private TollTransaction tollTransaction;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private String description;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
