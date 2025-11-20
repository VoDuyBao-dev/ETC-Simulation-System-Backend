package com.example.ETCSystem.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.example.ETCSystem.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallet_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @NonNull
    private TransactionType transactionType;

    @Column(name = "balance_after")
    private BigDecimal balanceAfter;

    private String description;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
