package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.common.WalletDTO;
import com.example.ETCSystem.dto.request.DeviceRequest;
import com.example.ETCSystem.entities.RfidTag;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.entities.Vehicle;
import com.example.ETCSystem.entities.Wallet;
import com.example.ETCSystem.enums.TagStatus;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.WalletMapper;
import com.example.ETCSystem.repositories.RfidTagRepository;
import com.example.ETCSystem.repositories.UserRepository;
import com.example.ETCSystem.repositories.WalletRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletService {
    WalletRepository walletRepository;
    WalletMapper walletMapper;
    RfidTagRepository rfidTagRepository;
    UserRepository userRepository;

    public WalletDTO getWalletByUserId(Long userId) {
        Wallet wallet = walletRepository.findByUser_UserId(userId).orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_EXISTED));
        return walletMapper.toWalletDTO(wallet);
    }

    public WalletDTO getWalletById(Long id) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_EXISTED));
        return walletMapper.toWalletDTO(wallet);
    }

    public Wallet getWalletByRfidTag(DeviceRequest deviceRequest){
        RfidTag rfidTag = rfidTagRepository.findByTagUid(deviceRequest.getRfidTagCode())
                .orElseThrow(() -> new AppException(ErrorCode.RFID_TAG_NOT_EXISTED));

        if(rfidTag.getStatus() != TagStatus.ACTIVE){
            throw new AppException(ErrorCode.RFID_TAG_NOT_ACTIVE);
        }

        Vehicle vehical = rfidTag.getVehicle();
        if(vehical == null){
            throw new AppException(ErrorCode.VEHICLE_NOT_EXISTED);
        }

        User user = vehical.getUser();
        if(user == null){
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        Wallet wallet = user.getWallet();
        if(wallet == null){
            throw new AppException(ErrorCode.WALLET_NOT_EXISTED);
        }else if(wallet.getIsBlocked() == true){
            throw new AppException(ErrorCode.WALLET_IS_BLOCKED);
        }

        return wallet;


    }

    public void addBalance(Long walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_EXISTED));

        if (wallet.getIsBlocked()) {
            throw new AppException(ErrorCode.WALLET_BLOCKED);
        }

        // Cập nhật số dư
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        log.info("Added {} to wallet {}", amount, walletId);
    }

    public BigDecimal getBalance() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if(user.getWallet() != null){
            BigDecimal balance = user.getWallet().getBalance();
            return balance;
        }else {
            throw new AppException(ErrorCode.WALLET_NOT_EXISTED);
        }


    }



}
