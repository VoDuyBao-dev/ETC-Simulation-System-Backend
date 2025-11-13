package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.common.WalletDTO;
import com.example.ETCSystem.entities.Wallet;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    WalletDTO toWalletDTO(Wallet wallet);
}
