package com.example.ETCSystem.projections;

import com.example.ETCSystem.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface WalletTransactionProjection {
    // Fields từ WalletTransaction
    LocalDateTime getCreatedAt();
    BigDecimal getAmount();
    String getDescription();
    TransactionType getTransactionType();
    BigDecimal getBalanceAfter();

    // Nested: Wallet (luôn có)
    WalletProjection getWallet();

    // Nested: TollTransaction (có thể null nếu TOPUP)
    TollTransactionProjection getTollTransaction();

    // ========== NESTED PROJECTIONS ==========

    // 1. WalletProjection (từ Wallet)
    interface WalletProjection {
        Long getId();      // Để tạo account code "E0000000001"
//        BigDecimal getBalance();  // Số dư sau giao dịch
    }

    // 2. TollTransactionProjection (từ TollTransaction - chỉ cho DEDUCT)
    interface TollTransactionProjection {
        StationProjection getStation();  // Trạm thu phí
        VehicleProjection getVehicle();  // Xe
    }

    // 3. StationProjection (từ Station)
    interface StationProjection {
        String getName();  // "Trạm An Sương"
    }

    // 4. VehicleProjection (từ Vehicle) ← BẠN CHƯA CÓ → THÊM VÀO
    interface VehicleProjection {
        String getPlateNumber();  // "51H-12345"
    }
}
