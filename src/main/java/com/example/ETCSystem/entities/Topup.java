package com.example.ETCSystem.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.example.ETCSystem.enums.TopupMethod;
import com.example.ETCSystem.enums.TopupStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "topups")

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
    private TopupMethod method; // bank, wallet, manual

    private String referenceCode;

    private String description;

    @Enumerated(EnumType.STRING)
    private TopupStatus status; // PENDING, COMPLETED, FAILED

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime completedAt;
}
