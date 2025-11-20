package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.common.TopupDTO;
import com.example.ETCSystem.dto.response.TransactionHistoryResponse;
import com.example.ETCSystem.entities.TollTransaction;
import com.example.ETCSystem.entities.Wallet;
import com.example.ETCSystem.entities.WalletTransaction;
import com.example.ETCSystem.enums.TopupStatus;
import com.example.ETCSystem.enums.TransactionType;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.projections.TollTransactionProjection;
import com.example.ETCSystem.projections.TopupHistory;
import com.example.ETCSystem.projections.WalletTransactionProjection;
import com.example.ETCSystem.repositories.WalletTransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletTransactionService {
    WalletTransactionRepository walletTransactionRepository;

    public Page<TransactionHistoryResponse> getHistory(Long userId, Pageable pageable) {
        Page<WalletTransactionProjection> page = walletTransactionRepository.findTransactionHistoryByUserId(userId, pageable);

        return page.map(this::toDTO);

    }

    private TransactionHistoryResponse toDTO(WalletTransactionProjection proj) {

        var wallet = proj.getWallet();
        if (wallet == null || wallet.getId() == null) {
            log.error("Wallet projection missing for transaction at {}", proj.getCreatedAt());
            throw new AppException(ErrorCode.WALLET_DATA_MISSING);
        }

        // amount: nạp tiền → dương, trừ tiền → âm
        BigDecimal amount = proj.getTransactionType() == TransactionType.DEDUCT
                ? proj.getAmount().negate()
                : proj.getAmount();

        String description = "Thu phí qua trạm";
        String stationName = null;
        String plateNumber = null;

        if (proj.getTransactionType() == TransactionType.TOPUP) {
            description = Optional.ofNullable(proj.getDescription())
                    .filter(s -> !s.isBlank())
                    .orElse("Nạp tiền vào ví");

        } else if (proj.getTransactionType() == TransactionType.DEDUCT) {

            WalletTransactionProjection.TollTransactionProjection toll = proj.getTollTransaction();

            if (toll != null && toll.getStation() != null && toll.getVehicle() != null) {
                stationName = toll.getStation().getName();
                plateNumber = toll.getVehicle().getPlateNumber();

                // Ưu tiên dùng description từ db
                description = Optional.ofNullable(proj.getDescription())
                        .filter(s -> !s.isBlank())
                        .orElse("Thu phí tại " + stationName);
            } else {
                description = Optional.ofNullable(proj.getDescription())
                        .filter(s -> !s.isBlank())
                        .orElse("Thu phí qua trạm");
            }
        }

        // 5. Trả về DTO
        return TransactionHistoryResponse.builder()
                .amount(amount)
                .dateTime(proj.getCreatedAt())
                .balanceAfter(proj.getBalanceAfter())
                .description(description)
                .stationName(stationName)
                .plateNumber(plateNumber)
                .build();
    }

    public void saveWalletTransaction(Wallet wallet, TollTransaction tt, BigDecimal amount, TransactionType transactionType, String description, BigDecimal balanceAfter) {
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .wallet(wallet)
                .tollTransaction(tt)
                .amount(amount)
                .transactionType(transactionType)
                .description(description)
                .balanceAfter(balanceAfter)
                .build();

        try {
            walletTransactionRepository.save(walletTransaction);
        } catch (Exception e) {
            throw new AppException(ErrorCode.SAVE_WALLET_TRANSACTION_FAILED);
        }
    }
}



