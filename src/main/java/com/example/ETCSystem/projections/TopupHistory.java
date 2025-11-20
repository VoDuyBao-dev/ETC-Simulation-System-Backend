package com.example.ETCSystem.projections;

import com.example.ETCSystem.enums.TopupMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TopupHistory {
    Long getId();
    BigDecimal getAmount();
    BigDecimal getBalanceAfter();
    TopupMethod getMethod();
    LocalDateTime getCreatedAt();
}
