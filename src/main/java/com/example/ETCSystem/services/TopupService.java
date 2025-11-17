package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.common.TopupDTO;
import com.example.ETCSystem.entities.Topup;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.entities.Wallet;
import com.example.ETCSystem.enums.TopupMethod;
import com.example.ETCSystem.enums.TopupStatus;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.TopupMapper;
import com.example.ETCSystem.repositories.TopupRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TopupService {
    TopupRepository topupRepository;
    TopupMapper topupMapper;

    public TopupDTO createTopup(User user, BigDecimal amount, String bankCode,TopupMethod method) {

        // Validate input
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        if (user.getWallet() == null) {
            throw new AppException(ErrorCode.WALLET_NOT_EXISTED);
        }

        String referenceCode = "REF" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);

        Topup topup = Topup.builder()
                .user(user)
                .wallet(user.getWallet())
                .amount(amount)
                .method(method)
                .referenceCode(referenceCode)
                .transactionNo(null)
                .bankCode(bankCode)
                .payDate(null)
                .vnpResponseCode(null)
                .description("Nạp tiền vào ví")
                .status(TopupStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .completedAt(null)
                .build();

        try{
            return topupMapper.toTopupDTO(topupRepository.save(topup));
        } catch (Exception e) {
            throw new AppException(ErrorCode.SAVE_TOPUP_FAILED);
        }

    }

    public TopupDTO getTopupByReferenceCode(String referenceCode){
        Topup topup = topupRepository.findByReferenceCode(referenceCode).orElseThrow(() -> new AppException(ErrorCode.TOPUP_NOT_EXISTED));
        return  topupMapper.toTopupDTO(topup);


    }
}
