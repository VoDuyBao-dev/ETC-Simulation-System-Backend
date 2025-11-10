package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.response.TransactionHistoryResponse;
import com.example.ETCSystem.entities.TollTransaction;
import com.example.ETCSystem.entities.Wallet;
import com.example.ETCSystem.entities.WalletTransaction;
import com.example.ETCSystem.enums.TransactionType;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.projections.WalletTransactionProjection;
import com.example.ETCSystem.repositories.WalletTransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletTransactionService {
    WalletTransactionRepository walletTransactionRepository;
    static String HOTLINE = "19001000";

    public Page<TransactionHistoryResponse> getHistory(Long userId, Pageable pageable) {
        Page<WalletTransactionProjection> page = walletTransactionRepository.findTransactionHistoryByUserId(userId, pageable);

        return page.map(this::toDTO);
    }

    private TransactionHistoryResponse toDTO(WalletTransactionProjection proj) {
        // 1. Kiểm tra wallet
        var wallet = proj.getWallet();
        if (wallet == null || wallet.getId() == null) {
            log.error("Wallet projection missing for transaction at {}", proj.getCreatedAt());
            throw new AppException(ErrorCode.WALLET_DATA_MISSING);
        }

        // 2. Tính amount
        var amount = proj.getTransactionType() == TransactionType.DEDUCT
                ? proj.getAmount().negate()
                : proj.getAmount();

        log.info("Transaction type: {}", proj.getTransactionType());
        log.info("proj amount: {}", proj.getAmount());
        log.info("final amount: {}", amount);

        // 3. Tạo description
        String desc = switch (proj.getTransactionType()) {
            case TOPUP -> Optional.ofNullable(proj.getDescription())
                    .filter(s -> !s.isBlank())
                    .orElse("Nạp tiền vào ví");
            case DEDUCT -> buildDeductDescription(proj.getTollTransaction());
        };

        // 4. Tạo account code
        String account = "E" + String.format("%010d", wallet.getId());

        return new TransactionHistoryResponse(
                account,
                amount,
                proj.getCreatedAt(),
                proj.getBalanceAfter(),
                desc,
                HOTLINE
        );
    }

    private String buildDeductDescription(WalletTransactionProjection.TollTransactionProjection tt) {
        if (tt == null) {
            log.warn("DEDUCT transaction without TollTransaction");
            return "Trừ tiền";
        }

        String station = Optional.ofNullable(tt.getStation())
                .map(WalletTransactionProjection.StationProjection::getName)
                .filter(s -> !s.isBlank())
                .orElse("");

        String plate = Optional.ofNullable(tt.getVehicle())
                .map(WalletTransactionProjection.VehicleProjection::getPlateNumber)
                .filter(p -> !p.isBlank())
                .map(p -> "xe " + p)
                .orElse("");

        return "Trừ phí qua trạm " + station + " " + plate;
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



