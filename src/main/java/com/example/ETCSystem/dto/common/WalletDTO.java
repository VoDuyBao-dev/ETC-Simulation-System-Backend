package com.example.ETCSystem.dto.common;

import com.example.ETCSystem.entities.Topup;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.entities.WalletTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO {

    private Long id;


    private User user;

    private BigDecimal balance = BigDecimal.ZERO;

    private Boolean isBlocked = false;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    private List<WalletTransaction> transactions;


    private List<Topup> topups;
}
